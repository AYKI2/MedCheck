package com.example.medcheckb8.db.service.impl;

import com.example.medcheckb8.db.config.jwt.JwtService;
import com.example.medcheckb8.db.dto.request.ProfileRequest;
import com.example.medcheckb8.db.dto.response.*;
import com.example.medcheckb8.db.entities.Account;
import com.example.medcheckb8.db.entities.Result;
import com.example.medcheckb8.db.entities.User;
import com.example.medcheckb8.db.exceptions.BadRequestException;
import com.example.medcheckb8.db.exceptions.NotFountException;
import com.example.medcheckb8.db.repository.UserRepository;
import com.example.medcheckb8.db.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtService service;
    private static final Logger logger = Logger.getLogger(User.class.getName());

    @Override
    public Set<UserResponse> getAllPatients(String word) {
        logger.info("Получение всех пациентов с поисковым термином: {}" + word);
        if (word == null) {
            return repository.getAllPatients();
        }
        return repository.getAllPatients(word);
    }

    @Override
    public SimpleResponse getProfile(ProfileRequest request) {
        Account account = service.getAccountInToken();
        account.getUser().setFirstName(request.firstName());
        account.getUser().setLastName(request.lastName());
        account.setEmail(request.email());
        account.getUser().setPhoneNumber(request.phoneNumber());
        repository.save(account.getUser());
        logger.log(Level.INFO, "Обновлен профиль: ID аккаунта={0}, Имя={1}, Фамилия={2}, Email={3}, Номер телефона={4}",
                new Object[]{account.getId(), account.getUser().getFirstName(), account.getUser().getLastName(),
                        account.getEmail(), account.getUser().getPhoneNumber()});
        return SimpleResponse.builder().status(HttpStatus.OK).message("Успешно обновлено!").build();

    }

    @Override
    public ProfileResponse getResult() {
        Account account = service.getAccountInToken();
        ProfileResponse response = new ProfileResponse();
        response.setId(account.getId());
        response.setFirstName(account.getUser().getFirstName());
        response.setLastName(account.getUser().getLastName());
        response.setPhoneNumber(account.getUser().getPhoneNumber());
        response.setEmail(account.getEmail());
        logger.log(Level.INFO, "Результат профиля: {0}", response.toString());
        return response;
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        try {
            if (id == 1) {
                throw new BadRequestException("Нельзя удалить пользователя с ID \"1\", так как этот ID принадлежит администратору");
            }
            User user = repository.findById(id).orElseThrow(() -> new NotFountException(String.format("Пользователь с ID: %s не найден", id)));
            repository.delete(user);
            logger.info("Пользователь с ID " + id + "  успешно удален");
            return SimpleResponse.builder().status(HttpStatus.OK).message("Пользователь удален").build();
        } catch (BadRequestException e) {
            logger.warning("Ошибка при удалении пользователя с ID " + id + ": " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @Override
    public UserGetResultResponse findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFountException(String.format("Пользователь с ID: %s не найден", id)));
        List<ResultUserResponse> responses = new ArrayList<>();
        for (Result result : user.getResults()) {
            responses.add(ResultUserResponse.builder()
                    .resultId(result.getId())
                    .dateOfIssue(result.getDateOfIssue())
                    .timeOfIssue(result.getTimeOfIssue())
                    .orderNumber(result.getOrderNumber())
                    .services(result.getDepartment().getName().getTranslate())
                    .file(result.getFile())
                    .build());
        }
        return UserGetResultResponse.builder()
                .patientId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getAccount().getEmail())
                .results(responses)
                .build();
    }
}
