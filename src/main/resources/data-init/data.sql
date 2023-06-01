insert into accounts (id, email, password, role)
VALUES (1, 'admin@gmail.com', '$2a$12$448IReue43rqpYW27HWLQO5JJmvYSyjDvpVlwijFQq.FotkxRepBm', 'ADMIN'),
       (2, 'kubanmuit@gmail.com', '$2a$12$edF8P1CF93xkBX.WsT.NAOqJ6CGJVLke45MXKcMNKJ3uaQrT7bvk6', 'PATIENT'),
       (3, 'elizashyrbaeva@gmail.com', '$2a$12$ZvV/fI8Tz8pcvaUt86ooa.x4ctJHmvch5mkpqRjdUKmfdFyiVVk1S', 'PATIENT'),
       (4, 'isxak@gmail.com', '$2a$12$dYWxM1tOa.mMqFCVJ/rikeZxYxF0qck9pr1ioSijJN2k5/vCUcCs2', 'PATIENT'),
       (5, 'uluk@gmail.com', '$2a$12$QBzhWSs8erQeKuqfz.ZxEO.thonK92aJz6cqnYff8zatE7MZMOdyu', 'PATIENT');


insert into users (id, first_name, last_name, phone_number, account_id)
values (1, 'Админ', 'Админов', '+996708281123', 1),
       (2, 'Кубан', 'Келсинбеков', '+996708281456', 2),
       (3, 'Элиза', 'Ашырбаева', '+996708281789', 3),
       (4, 'Исхак', 'Абдухамитов', '+996550434204', 4),
       (5, 'Улук', 'Исманов', '+996708281334', 5);

insert into departments (id, name)
values (1, 'ALLERGOLOGY'),
       (2, 'ANESTHESIOLOGY'),
       (3, 'VACCINATION'),
       (4, 'GASTROENTEROLOGY'),
       (5, 'GYNECOLOGY'),
       (6, 'DERMATOLOGY'),
       (7, 'CARDIOLOGY'),
       (8, 'NEUROLOGY'),
       (9, 'NEUROSURGERY'),
       (10, 'ONCOLOGY'),
       (11, 'ORTHOPEDICS'),
       (12, 'OTORHINOLARYNGOLOGY'),
       (13, 'OPHTHALMOLOGY'),
       (14, 'PROCTOLOGY'),
       (15, 'PSYCHOTHERAPY'),
       (16, 'PULMONOLOGY'),
       (17, 'RHEUMATOLOGY'),
       (18, 'THERAPY'),
       (19, 'UROLOGY'),
       (20, 'PHLEBOLOGY'),
       (21, 'PHYSIOTHERAPY'),
       (22, 'ENDOCRINOLOGY');


insert into doctors (id, description, first_name, image, is_active, last_name, position, department_id)
values (1, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Асема', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525191319doctor1M.png', true, 'Асанова', 'Главный врач', 1),
       (2, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алина', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525248931doctor2F.png', true, 'Романова', 'Медсестра', 2),
       (3, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Мээрим', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525324420doctor4F.png', true, 'Жуманазарова', 'Врач аллерголог', 3),
       (4, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Каныкей', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525363027doctor5F.png', true, 'Уланбекова', 'Врач аллерголог', 2),
       (5, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Наталия', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525427375doctor7F.png', true, 'Романова', 'Врач анестезио́лог-реанимато́лог', 3),
       (6, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Галя', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525456926doctor8F.png', true, 'Кадырова', 'Врач анестезио́лог', 3),
       (7, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Лариса', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525487580doctor9F.png', true, 'Гузеева', 'Врач', 4),
       (8, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Артем', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525286461doctor3M.png', true, 'Новиков', 'Медсестра', 4),
       (9, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Мария', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525552691doctor11F.png', true, 'Шарапова', 'Врач-гастроэнтеролог', 5),
       (10, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алла', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525427375doctor7F.png', true, 'Пугачова', 'Врач-гастроэнтеролог', 5),
       (11, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Асел', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525552691doctor11F.png', true, 'Нубаева', 'Врач-гинеколг', 6),
       (12, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Адыл', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525607535doctor12M.png', true, 'Нурбеков', 'Врач-гинеколг', 6),
       (13, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Курсант', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525515086doctor10M.png', true, 'Базарбаев', 'Врач-дерматолог', 7),
       (14, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алима', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525324420doctor4F.png', true, 'Кочкорова', 'Врач-дерматолог', 7),
       (15, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Адахан', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525639250doctor13M.png', true, 'Таалайбеков', 'Врач-кардиолог', 8),
       (16, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алихан', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525733674doctor14M.png', true, 'Байсалов', 'Врач-кардиолог', 8),
       (17, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Нуржигит', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525789249doctor15M.png', true, 'Каныбеков', 'Врач-невролог', 9),
       (18, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Назима', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525552691doctor11F.png', true, 'Турдиева', 'Врач-невролог', 9),
       (19, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алтынай', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525324420doctor4F.png', true, 'Ташиева', 'Врач-эпилептолог', 10),
       (20, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Сагын', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525607535doctor12M.png', true, 'Чолпонбеков', 'Врач-эпилептолог', 10),
       (21, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Канышай', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525248931doctor2F.png', true, 'Чекирова', 'Врач-онколог', 11),
       (22, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Сыймые', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525515086doctor10M.png', true, 'Сапаров', 'Врач-онколог', 11),
       (23, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Бекзаман', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525191319doctor1M.png', true, 'Кочкорбаев', 'Врач-ортапед', 12),
       (24, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Данияр', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525400057doctor6M.png', true, 'Эрматов', 'Врач-ортапед', 12),
       (25, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Элзар', 'https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685525286461doctor3M.png', true, 'Кадырбаев', 'Врач-оториноларинголог', 13);
--        (26, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Нурмат', 'link image', true, 'Садыров', 'Врач-оториноларинголог', 13),
--        (27, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Жумгалбек', 'link image', true, 'Арстанбеков', 'Врач-офталмолог', 14),
--        (28, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Алтынай', 'link image', true, 'Нарбаева', 'Врач-офталмолог', 14),
--        (29, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Садыр', 'link image', false, 'Жапаров', 'Врач-проктолог', 15),
--        (30, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Акылбек', 'link image', true, 'Жапаров', 'Врач-проктолог', 15),
--        (31, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Камчы', 'link image', true, 'Ташиев', 'Врач-психолог', 16),
--        (32, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Камчы', 'link image', true, 'Колбаев', 'Врач-психолог', 16),
--        (33, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Жазгул', 'link image', true, 'Жайлообекова', 'Врач-пульманолог', 17),
--        (34, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Жанара', 'link image', true, 'Жылдызбекова', 'Врач-пульманолог', 17),
--        (35, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Марсел', 'link image', true, 'Жээнбеков', 'Врач', 18),
--        (36, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Жылдыз', 'link image', true, 'Туранов', 'Врач', 18),
--        (37, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Сергей', 'link image', true, 'Тарасов', 'Врач-терапевт', 19),
--        (38, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Анна', 'link image', true, 'Наталиевна', 'Врач-терапевт', 19),
--        (39, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Антон', 'link image', true, 'Чехов', 'Врач-уролог', 20),
--        (40, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Павел', 'link image', false, 'Пушкин', 'Врач-уролог', 20),
--        (41, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Николай', 'link image', true, 'Николаевич', 'Врач-сердечно-сосудистый хирург', 21),
--        (42, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Димитрий', 'link image', false, 'Нагиев', 'Врач-сердечно-сосудистый хирург', 21),
--        (43, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Иса', 'link image', true, 'Ахунбаев', 'Врач-физиотерапевт', 22),
--        (44, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Аман', 'link image', true, 'Жолдошов', 'Врач-физиотерапевт', 22),
--        (45, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Нариман', 'link image', true, 'Келсинбеков', 'Врач-эндокринолог', 22),
--        (46, 'Мы развиваемся, учимся и оттачиваем мастерство, стажируемся в ведущих университетах Европы, чтобы еще на шаг стать ближе к совершенству.', 'Карима', 'link image', false, 'Аскарбекова', 'Врач-эндокринолог', 22);

insert into appointments (id, date_of_visit, time_of_visit, email, full_name, phone_number, status, department_id, doctor_id, user_id)
values (1, '2023-06-01','11:00:00','kuban@gmail.com' ,'Кубан Келсинбеков', '0999888777', 'CONFIRMED', 1, 1, 1),
       (5, '2023-06-01','13:00:00','kuban@gmail.com' ,'Кубан Келсинбеков', '0999888777', 'CONFIRMED', 2, 2, 1),
       (2, '2023-06-02','14:00:00','elizf@gmail.com' ,'Элиза Ашырбаева', '0999888777', 'CANCELED', 2, 2, 2),
       (3, '2023-06-23','15:00:00','isxak@gmail.com' ,'Исхак Абдухамидов', '0999888777', 'CONFIRMED', 3, 3, 3),
       (4, '2023-06-19','08:00:00','uluk@gmail.com' ,'Улук Исманов', '0999888777', 'COMPLETED', 3, 3, 4);


insert into schedules (id, data_of_finish, data_of_start, start_break, end_break, interval_of_hours, department_id, doctor_id)
values (1, '2023-05-09', '2023-05-30', '12:00:00', '13:00:00', 60, 1, 1),
       (2, '2023-06-02', '2023-05-30', '12:00:00', '13:00:00', 60, 2, 2),
       (3, '2023-06-23', '2023-06-16', '12:00:00', '13:00:00', 60, 3, 3),
       (4, '2023-06-09', '2023-06-02', '12:00:00', '13:00:00', 60, 4, 7),
       (5, '2023-06-12', '2023-06-05', '12:00:00', '13:00:00', 60, 5, 9),
       (6, '2023-06-16', '2023-06-09', '12:00:00', '13:00:00', 60, 6, 11),
       (7, '2023-06-16', '2023-06-09', '12:00:00', '13:00:00', 60, 7, 13);


insert into schedule_date_and_times (id, date, is_busy, time_from, time_to, schedule_id)
values (1, '2023-06-01', false, '08:00:00', '09:00:00', 1),
       (2, '2023-06-01', false, '09:00:00', '10:00:00', 1),
       (3, '2023-06-01', false, '10:00:00', '11:00:00', 1),
       (4, '2023-06-01', true, '11:00:00', '12:00:00', 1),
       (5, '2023-06-01', false, '13:00:00', '14:00:00', 1),
       (6, '2023-06-01', true, '14:00:00', '15:00:00', 1),
       (7, '2023-06-01', false, '15:00:00', '16:00:00', 1),
       (8, '2023-06-01', true, '16:00:00', '17:00:00', 1),
       (9, '2023-06-01', true, '17:00:00', '18:00:00', 1),

       (10, '2023-06-02', false, '08:00:00', '09:00:00', 1),
       (11, '2023-06-02', true, '09:00:00', '10:00:00', 1),
       (12, '2023-06-02', false, '11:00:00', '12:00:00', 1),
       (13, '2023-06-02', false, '13:00:00', '14:00:00', 1),
       (14, '2023-06-02', false, '14:00:00', '15:00:00', 1),
       (15, '2023-06-02', false, '15:00:00', '16:00:00', 1),
       (16, '2023-06-02', true, '16:00:00', '17:00:00', 1),
       (17, '2023-06-02', true, '17:00:00', '18:00:00', 1),

       (18, '2023-06-06', false, '08:00:00', '09:00:00', 1),
       (19, '2023-06-06', false, '09:00:00', '10:00:00', 1),
       (20, '2023-06-06', false, '10:00:00', '11:00:00', 1),
       (21, '2023-06-06', false, '11:00:00', '12:00:00', 1),
       (22, '2023-06-06', false, '13:00:00', '14:00:00', 1),
       (23, '2023-06-06', false, '14:00:00', '15:00:00', 1),
       (24, '2023-06-06', true, '15:00:00', '16:00:00', 1),
       (25, '2023-06-06', false, '16:00:00', '17:00:00', 1),
       (26, '2023-06-06', false, '17:00:00', '18:00:00', 1),

       (27, '2023-06-08', true, '08:00:00', '09:00:00', 1),
       (28, '2023-06-08', false, '09:00:00', '10:00:00', 1),
       (29, '2023-06-08', false, '10:00:00', '11:00:00', 1),
       (30, '2023-06-08', false, '11:00:00', '12:00:00', 1),
       (31, '2023-06-08', false, '13:00:00', '14:00:00', 1),
       (32, '2023-06-08', false, '14:00:00', '15:00:00', 1),
       (33, '2023-06-08', true, '15:00:00', '16:00:00', 1),
       (34, '2023-06-08', false, '16:00:00', '17:00:00', 1),
       (35, '2023-06-08', false, '17:00:00', '18:00:00', 1),

       (36, '2023-06-09', false, '08:00:00', '09:00:00', 1),
       (37, '2023-06-09', false, '09:00:00', '10:00:00', 1),
       (38, '2023-06-09', true, '10:00:00', '11:00:00', 1),
       (39, '2023-06-09', false, '11:00:00', '12:00:00', 1),
       (40, '2023-06-09', false, '13:00:00', '14:00:00', 1),
       (41, '2023-06-09', false, '14:00:00', '15:00:00', 1),
       (42, '2023-06-09', false, '15:00:00', '16:00:00', 1),
       (43, '2023-06-09', false, '16:00:00', '17:00:00', 1),
       (44, '2023-06-09', true, '17:00:00', '18:00:00', 1),


       (45, '2023-05-30', false, '08:00:00', '09:00:00', 2),
       (46, '2023-05-30', true, '09:00:00', '10:00:00', 2),
       (47, '2023-05-30', false, '10:00:00', '11:00:00', 2),
       (48, '2023-05-30', false, '11:00:00', '12:00:00', 2),
       (49, '2023-05-30', false, '13:00:00', '14:00:00', 2),
       (50, '2023-05-30', false, '14:00:00', '15:00:00', 2),
       (51, '2023-05-30', false, '15:00:00', '16:00:00', 2),
       (52, '2023-05-30', false, '16:00:00', '17:00:00', 2),
       (53, '2023-05-30', false, '17:00:00', '18:00:00', 2),

       (54, '2023-05-31', false, '08:00:00', '09:00:00', 2),
       (55, '2023-05-31', false, '09:00:00', '10:00:00', 2),
       (56, '2023-05-31', false, '10:00:00', '11:00:00', 2),
       (57, '2023-05-31', false, '11:00:00', '12:00:00', 2),
       (58, '2023-05-31', false, '13:00:00', '14:00:00', 2),
       (59, '2023-05-31', false, '14:00:00', '15:00:00', 2),
       (60, '2023-05-31', false, '15:00:00', '16:00:00', 2),
       (61, '2023-05-31', true, '16:00:00', '17:00:00', 2),
       (62, '2023-05-31', true, '17:00:00', '18:00:00', 2),

       (63, '2023-06-01', false, '08:00:00', '09:00:00', 2),
       (64, '2023-06-01', false, '09:00:00', '10:00:00', 2),
       (65, '2023-06-01', false, '10:00:00', '11:00:00', 2),
       (66, '2023-06-01', false, '11:00:00', '12:00:00', 2),
       (67, '2023-06-01', true, '13:00:00', '14:00:00', 2),
       (68, '2023-06-01', false, '14:00:00', '15:00:00', 2),
       (69, '2023-06-01', false, '15:00:00', '16:00:00', 2),
       (70, '2023-06-01', false, '16:00:00', '17:00:00', 2),
       (71, '2023-06-01', false, '17:00:00', '18:00:00', 2),

       (72, '2023-06-02', false, '08:00:00', '09:00:00', 2),
       (73, '2023-06-02', false, '09:00:00', '10:00:00', 2),
       (74, '2023-06-02', false, '10:00:00', '11:00:00', 2),
       (75, '2023-06-02', false, '11:00:00', '12:00:00', 2),
       (76, '2023-06-02', true, '13:00:00', '14:00:00', 2),
       (77, '2023-06-02', true, '14:00:00', '15:00:00', 2),
       (78, '2023-06-02', false, '15:00:00', '16:00:00', 2),
       (79, '2023-06-02', false, '16:00:00', '17:00:00', 2),
       (80, '2023-06-02', false, '17:00:00', '18:00:00', 2),



       (81, '2023-06-19', true, '08:00:00', '09:00:00', 3),
       (82, '2023-06-19', false, '09:00:00', '10:00:00', 3),
       (83, '2023-06-19', false, '10:00:00', '11:00:00', 3),
       (84, '2023-06-19', false, '11:00:00', '12:00:00', 3),
       (85, '2023-06-19', false, '13:00:00', '14:00:00', 3),
       (86, '2023-06-19', false, '14:00:00', '15:00:00', 3),
       (87, '2023-06-19', false, '15:00:00', '16:00:00', 3),
       (88, '2023-06-19', false, '16:00:00', '17:00:00', 3),
       (89, '2023-06-19', false, '17:00:00', '18:00:00', 3),

       (90, '2023-06-20', true, '08:00:00', '09:00:00', 3),
       (91, '2023-06-20', false, '09:00:00', '10:00:00', 3),
       (92, '2023-06-20', false, '10:00:00', '11:00:00', 3),
       (93, '2023-06-20', false, '11:00:00', '12:00:00', 3),
       (94, '2023-06-20', false, '13:00:00', '14:00:00', 3),
       (95, '2023-06-20', true, '14:00:00', '15:00:00', 3),
       (96, '2023-06-20', false, '15:00:00', '16:00:00', 3),
       (97, '2023-06-20', false, '16:00:00', '17:00:00', 3),
       (98, '2023-06-20', false, '17:00:00', '18:00:00', 3),

       (99, '2023-06-21', false, '08:00:00', '09:00:00', 3),
       (100, '2023-06-21', false, '09:00:00', '10:00:00', 3),
       (101, '2023-06-21', true, '10:00:00', '11:00:00', 3),
       (102, '2023-06-21', false, '11:00:00', '12:00:00', 3),
       (103, '2023-06-21', false, '13:00:00', '14:00:00', 3),
       (104, '2023-06-21', false, '14:00:00', '15:00:00', 3),
       (105, '2023-06-21', false, '15:00:00', '16:00:00', 3),
       (106, '2023-06-21', false, '16:00:00', '17:00:00', 3),
       (107, '2023-06-21', false, '17:00:00', '18:00:00', 3),

       (108, '2023-06-22', false, '08:00:00', '09:00:00', 3),
       (109, '2023-06-22', false, '09:00:00', '10:00:00', 3),
       (110, '2023-06-22', false, '10:00:00', '11:00:00', 3),
       (111, '2023-06-22', false, '11:00:00', '12:00:00', 3),
       (112, '2023-06-22', false, '13:00:00', '14:00:00', 3),
       (113, '2023-06-22', true, '14:00:00', '15:00:00', 3),
       (114, '2023-06-22', false, '15:00:00', '16:00:00', 3),
       (115, '2023-06-22', false, '16:00:00', '17:00:00', 3),
       (116, '2023-06-22', false, '17:00:00', '18:00:00', 3),

       (117, '2023-06-23', false, '08:00:00', '09:00:00', 3),
       (118, '2023-06-23', false, '09:00:00', '10:00:00', 3),
       (119, '2023-06-23', false, '10:00:00', '11:00:00', 3),
       (120, '2023-06-23', false, '11:00:00', '12:00:00', 3),
       (121, '2023-06-23', false, '13:00:00', '14:00:00', 3),
       (122, '2023-06-23', false, '14:00:00', '15:00:00', 3),
       (123, '2023-06-23', true, '15:00:00', '16:00:00', 3),
       (124, '2023-06-23', false, '16:00:00', '17:00:00', 3),
       (125, '2023-06-23', false, '17:00:00', '18:00:00', 3),


       (126, '2023-06-05', false, '17:00:00', '18:00:00', 4),
       (127, '2023-06-05', false, '08:00:00', '09:00:00', 4),
       (128, '2023-06-05', false, '09:00:00', '10:00:00', 4),
       (129, '2023-06-05', false, '10:00:00', '11:00:00', 4),
       (130, '2023-06-05', false, '11:00:00', '12:00:00', 4),
       (131, '2023-06-05', true, '13:00:00', '14:00:00', 4),
       (132, '2023-06-05', false, '14:00:00', '15:00:00', 4),
       (133, '2023-06-05', false, '15:00:00', '16:00:00', 4),
       (134, '2023-06-05', true, '16:00:00', '17:00:00', 4),
       (135, '2023-06-05', true, '17:00:00', '18:00:00', 4),

       (136, '2023-06-06', false, '08:00:00', '09:00:00', 4),
       (137, '2023-06-06', false, '09:00:00', '10:00:00', 4),
       (138, '2023-06-06', false, '10:00:00', '11:00:00', 4),
       (139, '2023-06-06', false, '11:00:00', '12:00:00', 4),
       (140, '2023-06-06', false, '13:00:00', '14:00:00', 4),
       (141, '2023-06-06', false, '14:00:00', '15:00:00', 4),
       (142, '2023-06-06', false, '15:00:00', '16:00:00', 4),
       (143, '2023-06-06', true, '16:00:00', '17:00:00', 4),
       (144, '2023-06-06', true, '17:00:00', '18:00:00', 4),

       (145, '2023-06-07', false, '08:00:00', '09:00:00', 4),
       (146, '2023-06-07', false, '09:00:00', '10:00:00', 4),
       (147, '2023-06-07', false, '10:00:00', '11:00:00', 4),
       (148, '2023-06-07', false, '11:00:00', '12:00:00', 4),
       (149, '2023-06-07', false, '13:00:00', '14:00:00', 4),
       (150, '2023-06-07', false, '14:00:00', '15:00:00', 4),
       (151, '2023-06-07', false, '15:00:00', '16:00:00', 4),
       (152, '2023-06-07', false, '16:00:00', '17:00:00', 4),
       (153, '2023-06-07', false, '17:00:00', '18:00:00', 4),

       (154, '2023-06-08', false, '08:00:00', '09:00:00', 4),
       (155, '2023-06-08', true, '09:00:00', '10:00:00', 4),
       (156, '2023-06-08', false, '10:00:00', '11:00:00', 4),
       (157, '2023-06-08', false, '11:00:00', '12:00:00', 4),
       (158, '2023-06-08', false, '13:00:00', '14:00:00', 4),
       (159, '2023-06-08', false, '14:00:00', '15:00:00', 4),
       (160, '2023-06-08', false, '15:00:00', '16:00:00', 4),
       (161, '2023-06-07', false, '16:00:00', '17:00:00', 4),
       (162, '2023-06-07', false, '17:00:00', '18:00:00', 4),

       (163, '2023-06-09', false, '08:00:00', '09:00:00', 4),
       (164, '2023-06-09', true, '09:00:00', '10:00:00', 4),
       (165, '2023-06-09', false, '10:00:00', '11:00:00', 4),
       (166, '2023-06-09', false, '11:00:00', '12:00:00', 4),
       (167, '2023-06-09', false, '13:00:00', '14:00:00', 4),
       (168, '2023-06-09', false, '14:00:00', '15:00:00', 4),
       (169, '2023-06-09', false, '15:00:00', '16:00:00', 4),
       (170, '2023-06-09', false, '16:00:00', '17:00:00', 4),
       (171, '2023-06-09', false, '17:00:00', '18:00:00', 4),


       (172, '2023-06-05', true, '08:00:00', '09:00:00', 5),
       (173, '2023-06-05', false, '09:00:00', '10:00:00', 5),
       (174, '2023-06-05', false, '10:00:00', '11:00:00', 5),
       (175, '2023-06-05', false, '11:00:00', '12:00:00', 5),
       (176, '2023-06-05', false, '13:00:00', '14:00:00', 5),
       (177, '2023-06-05', false, '14:00:00', '15:00:00', 5),
       (178, '2023-06-05', false, '15:00:00', '16:00:00', 5),
       (179, '2023-06-05', false, '16:00:00', '17:00:00', 5),

       (180, '2023-06-06', false, '08:00:00', '09:00:00', 5),
       (181, '2023-06-06', false, '09:00:00', '10:00:00', 5),
       (182, '2023-06-06', false, '10:00:00', '11:00:00', 5),
       (183, '2023-06-06', false, '11:00:00', '12:00:00', 5),
       (184, '2023-06-06', false, '13:00:00', '14:00:00', 5),
       (185, '2023-06-06', false, '14:00:00', '15:00:00', 5),
       (186, '2023-06-06', false, '15:00:00', '16:00:00', 5),
       (187, '2023-06-06', false, '16:00:00', '17:00:00', 5),

       (188, '2023-06-07', false, '08:00:00', '09:00:00', 5),
       (189, '2023-06-07', false, '09:00:00', '10:00:00', 5),
       (190, '2023-06-07', false, '10:00:00', '11:00:00', 5),
       (191, '2023-06-07', false, '11:00:00', '12:00:00', 5),
       (192, '2023-06-07', false, '13:00:00', '14:00:00', 5),
       (193, '2023-06-07', false, '14:00:00', '15:00:00', 5),
       (194, '2023-06-07', false, '15:00:00', '16:00:00', 5),
       (195, '2023-06-07', false, '16:00:00', '17:00:00', 5),

       (196, '2023-06-08', false, '08:00:00', '09:00:00', 5),
       (197, '2023-06-08', false, '09:00:00', '10:00:00', 5),
       (198, '2023-06-08', false, '10:00:00', '11:00:00', 5),
       (199, '2023-06-08', false, '11:00:00', '12:00:00', 5),
       (200, '2023-06-08', false, '13:00:00', '14:00:00', 5),
       (201, '2023-06-08', false, '14:00:00', '15:00:00', 5),
       (202, '2023-06-08', true, '15:00:00', '16:00:00', 5),
       (203, '2023-06-08', false, '16:00:00', '17:00:00', 5),

       (204, '2023-06-09', false, '08:00:00', '09:00:00', 5),
       (205, '2023-06-09', false, '09:00:00', '10:00:00', 5),
       (206, '2023-06-09', false, '10:00:00', '11:00:00', 5),
       (207, '2023-06-09', false, '11:00:00', '12:00:00', 5),
       (208, '2023-06-09', false, '13:00:00', '14:00:00', 5),
       (209, '2023-06-09', false, '14:00:00', '15:00:00', 5),
       (210, '2023-06-09', true, '15:00:00', '16:00:00', 5),
       (211, '2023-06-09', false, '16:00:00', '17:00:00', 5),


       (212, '2023-06-09', false, '08:00:00', '09:00:00', 6),
       (213, '2023-06-09', false, '09:00:00', '10:00:00', 6),
       (214, '2023-06-09', false, '10:00:00', '11:00:00', 6),
       (215, '2023-06-09', false, '11:00:00', '12:00:00', 6),
       (216, '2023-06-09', false, '13:00:00', '14:00:00', 6),
       (217, '2023-06-09', false, '14:00:00', '15:00:00', 6),
       (218, '2023-06-09', true, '15:00:00', '16:00:00', 6),
       (219, '2023-06-09', false, '16:00:00', '17:00:00', 6),

       (220, '2023-06-12', false, '08:00:00', '09:00:00', 6),
       (221, '2023-06-12', false, '09:00:00', '10:00:00', 6),
       (222, '2023-06-12', true, '10:00:00', '11:00:00', 6),
       (223, '2023-06-12', false, '11:00:00', '12:00:00', 6),
       (224, '2023-06-12', false, '13:00:00', '14:00:00', 6),
       (225, '2023-06-12', false, '14:00:00', '15:00:00', 6),
       (226, '2023-06-12', true, '15:00:00', '16:00:00', 6),
       (227, '2023-06-12', false, '16:00:00', '17:00:00', 6),

       (228, '2023-06-13', false, '08:00:00', '09:00:00', 6),
       (229, '2023-06-13', true, '09:00:00', '10:00:00', 6),
       (230, '2023-06-13', false, '10:00:00', '11:00:00', 6),
       (231, '2023-06-13', false, '11:00:00', '12:00:00', 6),
       (232, '2023-06-13', false, '13:00:00', '14:00:00', 6),
       (233, '2023-06-13', false, '14:00:00', '15:00:00', 6),
       (234, '2023-06-13', true, '15:00:00', '16:00:00', 6),
       (235, '2023-06-13', false, '16:00:00', '17:00:00', 6),

       (236, '2023-06-14', false, '08:00:00', '09:00:00', 6),
       (237, '2023-06-14', false, '09:00:00', '10:00:00', 6),
       (238, '2023-06-14', false, '10:00:00', '11:00:00', 6),
       (239, '2023-06-14', true, '11:00:00', '12:00:00', 6),
       (240, '2023-06-14', false, '13:00:00', '14:00:00', 6),
       (241, '2023-06-14', false, '14:00:00', '15:00:00', 6),
       (242, '2023-06-14', false, '15:00:00', '16:00:00', 6),
       (243, '2023-06-14', true, '16:00:00', '17:00:00', 6),

       (244, '2023-06-16', false, '08:00:00', '09:00:00', 6),
       (245, '2023-06-16', false, '09:00:00', '10:00:00', 6),
       (246, '2023-06-16', false, '10:00:00', '11:00:00', 6),
       (247, '2023-06-16', false, '11:00:00', '12:00:00', 6),
       (248, '2023-06-16', true, '13:00:00', '14:00:00', 6),
       (249, '2023-06-16', false, '14:00:00', '15:00:00', 6),
       (250, '2023-06-16', true, '15:00:00', '16:00:00', 6),
       (251, '2023-06-16', false, '16:00:00', '17:00:00', 6),


       (252, '2023-06-09', false, '08:00:00', '09:00:00', 7),
       (253, '2023-06-09', false, '09:00:00', '10:00:00', 7),
       (254, '2023-06-09', false, '10:00:00', '11:00:00', 7),
       (255, '2023-06-09', true, '11:00:00', '12:00:00', 7),
       (256, '2023-06-09', false, '13:00:00', '14:00:00', 7),
       (257, '2023-06-09', false, '14:00:00', '15:00:00', 7),
       (258, '2023-06-09', false, '15:00:00', '16:00:00', 7),
       (259, '2023-06-09', false, '16:00:00', '17:00:00', 7),

       (260, '2023-06-13', false, '08:00:00', '09:00:00', 7),
       (261, '2023-06-13', false, '09:00:00', '10:00:00', 7),
       (262, '2023-06-13', true, '10:00:00', '11:00:00', 7),
       (263, '2023-06-13', false, '11:00:00', '12:00:00', 7),
       (264, '2023-06-13', false, '13:00:00', '14:00:00', 7),
       (265, '2023-06-13', false, '14:00:00', '15:00:00', 7),
       (266, '2023-06-13', true, '15:00:00', '16:00:00', 7),
       (267, '2023-06-13', false, '16:00:00', '17:00:00', 7),

       (268, '2023-06-14', false, '08:00:00', '09:00:00', 7),
       (269, '2023-06-14', false, '09:00:00', '10:00:00', 7),
       (270, '2023-06-14', false, '10:00:00', '11:00:00', 7),
       (271, '2023-06-14', true, '11:00:00', '12:00:00', 7),
       (272, '2023-06-14', false, '13:00:00', '14:00:00', 7),
       (273, '2023-06-14', false, '14:00:00', '15:00:00', 7),
       (274, '2023-06-14', true, '15:00:00', '16:00:00', 7),
       (275, '2023-06-14', false, '16:00:00', '17:00:00', 7),

       (276, '2023-06-15', false, '08:00:00', '09:00:00', 7),
       (277, '2023-06-15', true, '09:00:00', '10:00:00', 7),
       (278, '2023-06-15', false, '10:00:00', '11:00:00', 7),
       (279, '2023-06-15', false, '11:00:00', '12:00:00', 7),
       (280, '2023-06-15', false, '13:00:00', '14:00:00', 7),
       (281, '2023-06-15', true, '14:00:00', '15:00:00', 7),
       (282, '2023-06-15', false, '15:00:00', '16:00:00', 7),
       (283, '2023-06-15', false, '16:00:00', '17:00:00', 7),

       (284, '2023-06-16', false, '08:00:00', '09:00:00', 7),
       (285, '2023-06-16', false, '09:00:00', '10:00:00', 7),
       (286, '2023-06-16', true, '10:00:00', '11:00:00', 7),
       (287, '2023-06-16', false, '11:00:00', '12:00:00', 7),
       (288, '2023-06-16', false, '13:00:00', '14:00:00', 7),
       (289, '2023-06-16', false, '14:00:00', '15:00:00', 7),
       (290, '2023-06-16', false, '15:00:00', '16:00:00', 7),
       (291, '2023-06-16', true, '16:00:00', '17:00:00', 7);


insert into results (id, date_of_issue, file, order_number, department_id, user_id)
values (1, '2023-06-02','https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685539128052hello.docx','88888888888', 1, 1),
       (2, '2023-06-30','https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685539128052hello.docx','99999999999', 3, 3), --appointment id
       (3, '2023-06-13','https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685539128052hello.docx','11111111111', 2, 2), --файл должен добавляться в s3
       (4, '2023-06-23','https://medcheckbucket.s3.eu-central-1.amazonaws.com/1685539128052hello.docx','22222222222', 3, 4); --отправка номера в email


insert into schedule_repeat_day (schedule_id, repeat_day, repeat_day_key)
values (1, false, 'MONDAY'),
       (1, true, 'TUESDAY'),
       (1, false, 'WEDNESDAY'),
       (1, true, 'THURSDAY'),
       (1, true, 'FRIDAY'),
       (1, false, 'SATURDAY'),
       (1, false, 'SUNDAY'),

       (2, true, 'MONDAY'),
       (2, true, 'TUESDAY'),
       (2, true, 'WEDNESDAY'),
       (2, true, 'THURSDAY'),
       (2, true, 'FRIDAY'),
       (2, false, 'SATURDAY'),
       (2, false, 'SUNDAY'),

       (3, true, 'MONDAY'),
       (3, true, 'TUESDAY'),
       (3, true, 'WEDNESDAY'),
       (3, true, 'THURSDAY'),
       (3, true, 'FRIDAY'),
       (3, false, 'SATURDAY'),
       (3, false, 'SUNDAY'),

       (4, true, 'MONDAY'),
       (4, true, 'TUESDAY'),
       (4, true, 'WEDNESDAY'),
       (4, true, 'THURSDAY'),
       (4, true, 'FRIDAY'),
       (4, false, 'SATURDAY'),
       (4, false, 'SUNDAY'),

       (5, true, 'MONDAY'),
       (5, true, 'TUESDAY'),
       (5, true, 'WEDNESDAY'),
       (5, true, 'THURSDAY'),
       (5, true, 'FRIDAY'),
       (5, false, 'SATURDAY'),
       (5, false, 'SUNDAY'),

       (6, true, 'MONDAY'),
       (6, true, 'TUESDAY'),
       (6, true, 'WEDNESDAY'),
       (6, false, 'THURSDAY'),
       (6, true, 'FRIDAY'),
       (6, false, 'SATURDAY'),
       (6, false, 'SUNDAY'),

       (7, false, 'MONDAY'),
       (7, true, 'TUESDAY'),
       (7, true, 'WEDNESDAY'),
       (7, true, 'THURSDAY'),
       (7, true, 'FRIDAY'),
       (7, false, 'SATURDAY'),
       (7, false, 'SUNDAY');


insert into applications (id, date, name, phone_number, processed)
VALUES (1, '2023-05-29', 'Асан Усонбаев', '+996709990099', true),
       (2, '2023-05-31', 'Усон Асанов', '+996709990099', false),
       (3, '2023-06-04', 'Эсен Аманов', '+996550434242', true),
       (4, '2023-06-02', 'Аман Эсенов', '+996507434204', false);