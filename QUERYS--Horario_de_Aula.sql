----------------------------------------------------------------------------------------------
--------- AUTOR: Jorge De Lima Rocha  --------------------------------------------------------
--------- BANCO: H2  				  --------------------------------------------------------
--------- LINGUAGEM: SQL  				  ----------------------------------------------------
----------------------------------------------------------------------------------------------


----------------------------------------------------------------------------------------------
--------- CRIAÇÃO DAS TABELAS  ---------------------------------------------------------------
----------------------------------------------------------------------------------------------

-- 1. Tabela de Prédios
CREATE TABLE IF NOT EXISTS BUILDING (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- 2. Tabela de Salas
CREATE TABLE IF NOT EXISTS ROOM (
    id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    FOREIGN KEY (building_id) REFERENCES BUILDING(id)
);

-- 3. Tabela de Professores
CREATE TABLE IF NOT EXISTS PROFESSOR (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(50) NOT NULL
);

-- 4. Tabela de Disciplinas
CREATE TABLE IF NOT EXISTS SUBJECT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL,
    credits INT NOT NULL,
    taught_by INT,
    FOREIGN KEY (taught_by) REFERENCES PROFESSOR(id)
);

-- 5. Tabela de Turmas (correção da palavra reservada "year")
CREATE TABLE IF NOT EXISTS CLASS (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    class_code VARCHAR(20) NOT NULL,
    semester VARCHAR(20) NOT NULL,
    academic_year INT NOT NULL,  -- Alterado de "year" para "academic_year"
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(id)
);

-- 6. Tabela de Horários de Aula
CREATE TABLE IF NOT EXISTS CLASS_SCHEDULE (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    room_id INT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (class_id) REFERENCES CLASS(id),
    FOREIGN KEY (room_id) REFERENCES ROOM(id)
);

-- 7. Tabela de Alunos
CREATE TABLE IF NOT EXISTS STUDENT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    enrollment_number VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL
);

-- 8. Tabela de Matrículas
CREATE TABLE IF NOT EXISTS ENROLLMENT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES STUDENT(id),
    FOREIGN KEY (class_id) REFERENCES CLASS(id)
);

----------------------------------------------------------------------------------------------
--------- INSERÇÃO DE DADOS   ----------------------------------------------------------------
----------------------------------------------------------------------------------------------

-- 1. Inserir Prédios
INSERT INTO BUILDING (name, address) VALUES
    ('Prédio Principal', 'Rua da Escola, 123'),
    ('Anexo de Ciências', 'Rua da Escola, 125'),
    ('Centro de Artes', 'Avenida Cultural, 500');

-- 2. Inserir Salas
INSERT INTO ROOM (building_id, room_number, capacity) VALUES
    (1, '101', 30),
    (1, '102', 25),
    (1, '201', 40),
    (2, 'Lab-01', 20),
    (2, 'Lab-02', 20),
    (3, 'Sala de Música', 15),
    (3, 'Auditório', 100);

-- 3. Inserir Professores
INSERT INTO PROFESSOR (name, email, phone, department) VALUES
    ('Professor Girafales', 'girafales@escola.edu', '95555-1234', 'Matemática'),
    ('Professora Dona Florinda', 'florinda@escola.edu', '95555-2345', 'Literatura'),
    ('Professor Seu Barriga', 'seubarriga@escola.edu', '95555-3456', 'Educação Física'),
    ('Professor Seu Madruga', 'seumadruga@escola.edu', '95555-4567', 'Música'),
    ('Professora Dona Clotilde', 'clotilde@escola.edu', '95555-5678', 'Ciências');

-- 4. Inserir Disciplinas
INSERT INTO SUBJECT (name, code, credits, taught_by) VALUES
    ('Matemática Básica', 'MAT101', 4, 1),
    ('Literatura Clássica', 'LIT201', 3, 2),
    ('Educação Física', 'EDF101', 2, 3),
    ('Teoria Musical', 'MUS101', 3, 4),
    ('Química Geral', 'QUI101', 4, 5),
    ('Álgebra Linear', 'MAT201', 4, 1),
    ('Poesia Moderna', 'LIT202', 3, 2),
    ('Esportes Coletivos', 'EDF102', 2, 3);

-- 5. Inserir Turmas (ajustado para usar academic_year)
INSERT INTO CLASS (subject_id, class_code, semester, academic_year) VALUES
    (1, 'MAT101-A', '1', 2025),
    (1, 'MAT101-B', '1', 2025),
    (2, 'LIT201-A', '1', 2025),
    (3, 'EDF101-A', '1', 2025),
    (4, 'MUS101-A', '1', 2025),
    (5, 'QUI101-A', '1', 2025),
    (6, 'MAT201-A', '1', 2025),
    (7, 'LIT202-A', '1', 2025),
    (8, 'EDF102-A', '1', 2025);

-- 6. Inserir Horários de Aula
INSERT INTO CLASS_SCHEDULE (class_id, room_id, day_of_week, start_time, end_time) VALUES
    -- Matemática Básica - Turma A
    (1, 1, 'Segunda', '08:00:00', '10:00:00'),
    (1, 1, 'Quarta', '08:00:00', '10:00:00'),
    
    -- Matemática Básica - Turma B
    (2, 1, 'Terça', '10:00:00', '12:00:00'),
    (2, 1, 'Quinta', '10:00:00', '12:00:00'),
    
    -- Literatura Clássica
    (3, 2, 'Segunda', '14:00:00', '16:00:00'),
    (3, 2, 'Quarta', '14:00:00', '16:00:00'),
    
    -- Educação Física
    (4, 7, 'Sexta', '08:00:00', '10:00:00'),
    
    -- Teoria Musical
    (5, 6, 'Terça', '16:00:00', '18:00:00'),
    (5, 6, 'Quinta', '16:00:00', '18:00:00'),
    
    -- Química Geral
    (6, 4, 'Segunda', '10:00:00', '12:00:00'),
    (6, 4, 'Quarta', '10:00:00', '12:00:00'),
    
    -- Álgebra Linear
    (7, 3, 'Terça', '08:00:00', '10:00:00'),
    (7, 3, 'Quinta', '08:00:00', '10:00:00'),
    
    -- Poesia Moderna
    (8, 2, 'Segunda', '16:00:00', '18:00:00'),
    (8, 2, 'Quarta', '16:00:00', '18:00:00'),
    
    -- Esportes Coletivos
    (9, 7, 'Sexta', '14:00:00', '16:00:00');

-- 7. Inserir Alunos (apenas personagens autênticos da Turma do Chaves)
INSERT INTO STUDENT (name, email, enrollment_number, birth_date) VALUES
    ('Chaves', 'chaves@escola.edu', '20250001', '2010-01-15'),
    ('Quico', 'quico@escola.edu', '20250002', '2010-03-20'),
    ('Nhonho', 'nhonho@escola.edu', '20250003', '2010-05-10'),
    ('Chiquinha', 'chiquinha@escola.edu', '20250004', '2010-07-25'),
    ('Godinez', 'godinez@escola.edu', '20250005', '2010-11-30'),
    ('Pópis', 'popis@escola.edu', '20250006', '2010-09-05'),
    ('Jaiminho Jr.', 'jaiminhojr@escola.edu', '20250007', '2010-12-12');

-- 8. Inserir Matrículas (ajustando para os alunos que permaneceram)
INSERT INTO ENROLLMENT (student_id, class_id, enrollment_date, status) VALUES
    -- Chaves
    (1, 1, '2025-01-15', 'ATIVO'),
    (1, 3, '2025-01-15', 'ATIVO'),
    (1, 4, '2025-01-15', 'ATIVO'),
    
    -- Quico
    (2, 2, '2025-01-16', 'ATIVO'),
    (2, 3, '2025-01-16', 'ATIVO'),
    (2, 4, '2025-01-16', 'ATIVO'),
    
    -- Nhonho
    (3, 2, '2025-01-16', 'ATIVO'),
    (3, 5, '2025-01-16', 'ATIVO'),
    (3, 6, '2025-01-16', 'ATIVO'),
    
    -- Chiquinha
    (4, 1, '2025-01-17', 'ATIVO'),
    (4, 5, '2025-01-17', 'ATIVO'),
    (4, 8, '2025-01-17', 'ATIVO'),
    
    -- Godinez
    (5, 2, '2025-01-17', 'ATIVO'),
    (5, 4, '2025-01-17', 'ATIVO'),
    (5, 9, '2025-01-17', 'ATIVO'),
    
    -- Pópis
    (6, 3, '2025-01-18', 'ATIVO'),
    (6, 5, '2025-01-18', 'ATIVO'),
    (6, 8, '2025-01-18', 'ATIVO'),
    
    -- Jaiminho Jr.
    (7, 7, '2025-01-18', 'ATIVO'),
    (7, 8, '2025-01-18', 'ATIVO'),
    (7, 9, '2025-01-18', 'ATIVO');

----------------------------------------------------------------------------------------------
--------- CONSULTAS  -------------------------------------------------------------------------
----------------------------------------------------------------------------------------------

-- Consulta 1: Quantidade de horas que cada professor tem comprometido em aulas
SELECT 
    p.id AS professor_id,
    p.name AS nome_professor,
    SUM(
        DATEDIFF('HOUR', cs.start_time, cs.end_time)
    ) AS horas_semanais
FROM 
    PROFESSOR p
    INNER JOIN SUBJECT s ON s.taught_by = p.id
    INNER JOIN CLASS c ON c.subject_id = s.id
    INNER JOIN CLASS_SCHEDULE cs ON cs.class_id = c.id
GROUP BY 
    p.id, p.name
ORDER BY 
    horas_semanais DESC;

-- Consulta 2: Lista de salas com horários livres e ocupados
SELECT 
    r.id AS room_id,
    r.building_id,
    b.name AS nome_predio,
    d.dia,
    h.hora AS horario,
    CASE 
        WHEN cs.class_id IS NULL THEN 'Livre'
        ELSE 'Ocupado' 
    END AS status,
    s.name AS disciplina,
    p.name AS professor
FROM 
    ROOM r
    INNER JOIN BUILDING b ON r.building_id = b.id
    CROSS JOIN (
        SELECT 'Segunda' AS dia UNION ALL SELECT 'Terça' UNION ALL SELECT 'Quarta' 
        UNION ALL SELECT 'Quinta' UNION ALL SELECT 'Sexta'
    ) d
    CROSS JOIN (
        SELECT '08:00:00' AS hora UNION ALL SELECT '09:00:00' UNION ALL SELECT '10:00:00'
        UNION ALL SELECT '11:00:00' UNION ALL SELECT '12:00:00' UNION ALL SELECT '13:00:00'
        UNION ALL SELECT '14:00:00' UNION ALL SELECT '15:00:00' UNION ALL SELECT '16:00:00'
        UNION ALL SELECT '17:00:00' UNION ALL SELECT '18:00:00' UNION ALL SELECT '19:00:00'
        UNION ALL SELECT '20:00:00' UNION ALL SELECT '21:00:00'
    ) h
    LEFT JOIN CLASS_SCHEDULE cs ON 
        cs.room_id = r.id AND 
        cs.day_of_week = d.dia AND 
        PARSEDATETIME(h.hora, 'HH:mm:ss') >= cs.start_time AND 
        PARSEDATETIME(h.hora, 'HH:mm:ss') < cs.end_time
    LEFT JOIN CLASS c ON cs.class_id = c.id
    LEFT JOIN SUBJECT s ON c.subject_id = s.id
    LEFT JOIN PROFESSOR p ON s.taught_by = p.id
WHERE
    r.id = 1  -- Filtre por uma sala específica para reduzir o resultado
ORDER BY 
    r.id, 
    CASE d.dia
        WHEN 'Segunda' THEN 1
        WHEN 'Terça' THEN 2
        WHEN 'Quarta' THEN 3
        WHEN 'Quinta' THEN 4
        WHEN 'Sexta' THEN 5
    END,
    h.hora;
