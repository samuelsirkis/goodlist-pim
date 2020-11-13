INSERT INTO public.categories (name) VALUES ('Lazer');
INSERT INTO public.categories (name) VALUES ('Alimentação');
INSERT INTO public.categories (name) VALUES ('Farmácia');
INSERT INTO public.categories (name) VALUES ('Supermercado');
INSERT INTO public.categories (name) VALUES ('Outros');

INSERT INTO public.users (name, email, password, role) VALUES('admin','admin@admin.com', '$2a$10$n45geoEk0cFCCaBzezKXc.Zg.vq15/GCmLmnulh.kmu4MSWbTK0UW', 'ADMIN');
INSERT INTO public.users (name, email, password, role) VALUES('user','user@user.com', '$2a$10$tLRX.1Q9XfAZJtty/h2tyO3yqhWoFHSIwN2qhHP12.x5HuTX6RsHy', 'USER');

INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Ana Santos Araujo', 'cliente1@cliente.com', '(85) 2284-7035', '12345678', '853.513.468-93', '1960-01-01', 'Rua do Abacaxi', '1040', null, 'Brasil', '38.400-12', 'Uberlândia', 'MG', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Renan Costa Silva', 'cliente3@cliente.com', '(31) 7719-5565', '12345678', '157.167.192-79', '1980-01-01', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11.400-12', 'Ribeirão Preto', 'SP', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Júlio Cardoso Almeida', 'cliente4@cliente.com', '(11) 6331-9289', '12345678', '922.074.588-73', '1970-01-01', 'Rua da Bateria', '23', null, 'Morumbi', '54.212-12', 'Goiânia', 'GO', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Leonor Barbosa Cavalcanti', 'cliente2@cliente.com', '(61) 9060-5148', '12345678', '831.836.515-13', '1999-01-01', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-12', 'Salvador', 'BA', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Larissa Santos Souza', 'cliente5@cliente.com', '(21) 5336-9131', '12345678', '685.784.987-04', '1985-06-06', 'Av Rio Branco', '321', null, 'Jardins', '56.400-12', 'Natal', 'RN', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Thiago Cardoso Ribeiro', 'cliente6@cliente.com', '(14) 6117-9176', '12345678', '252.243.166-85', '1998-01-01', 'Av Brasil', '100', null, 'Tubalina', '77.400-12', 'Porto Alegre', 'RS', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Beatriz Cardoso Pinto', 'cliente7@cliente.com', '(16) 5417-3520', '12345678', '527.137.415-72', '1990-01-01', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-12', 'Rio de Janeiro', 'RJ', 'ACTIVE');
INSERT INTO public.customers (name, email, phone, password, cpf, birthdate, street, number, complement, district, zip_code, city, state, status) VALUES (
'Victor Castro Alves', 'cliente8@cliente.com', '(67) 3341-9574', '12345678', '703.766.940-76', '1986-01-01', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-12', 'Manaus', 'AM', 'ACTIVE');


INSERT INTO public.providers (name, email, responsible, phone, password, cnpj, address, city, state, zip_code, status) VALUES (
'Fábricas Branco Ltda.', 'provider1@provider.com', 'Luiza Alves Gomes', '(19) 5411-2670', '12345678', '67.762.675/0001-49',  'Rua Quatorze, 1651', 'Americana', 'SP', '13471-261', 'ACTIVE');
INSERT INTO public.providers (name, email, responsible, phone, password, cnpj, address, city, state, zip_code, status) VALUES (
'Mania Fitness Ltda.', 'provider2@provider.com', 'Bianca Souza', '(48) 2950-2330', '12345678', '94.065.313/0001-71', 'Servidão Osnildo Leôncio Duarte, 95', 'Florianópolis', 'SC', '88066-575', 'ACTIVE');
INSERT INTO public.providers (name, email, responsible, phone, password, cnpj, address, city, state, zip_code, status) VALUES (
'Companhia das Delícias Comércio de Alimentos Ltda.', 'provider3@provider.com', 'Breno Lima Azevedo', '(21) 5010-3193', '12345678', '67.058.441/0001-15', 'Rua Coronel Gomes Machado, 934', 'Niterói', 'RJ', '24020-108', 'ACTIVE');
INSERT INTO public.providers (name, email, responsible, phone, password, cnpj, address, city, state, zip_code, status) VALUES (
'Tupi Têxtil S.A.', 'provider4@provider.com', 'Laura Pinto Souza', '(11) 4763-5078', '12345678', '49.602.666/0001-09', 'Rua Jorge Fernandez, 176', 'São Paulo', 'SP', '05857-310', 'ACTIVE');