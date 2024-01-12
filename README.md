# SD_T2
SD_TrabalhoPratico2

Neste trabalho pratico irei tentar aplicar algumas das tecnicas aprendidas na disciplina de Sistemas Distribuidos usando tecnologias diferentes.

## SeekArtist 2.0 - Sistema de Gestão de Artistas de Rua

Neste trabalho a ideia é implementar o que se encontra no PDF. Normalmente a ideia seria continuar com a implementação da primeira parte,
mas neste trabalho pede que se possa usar tecnologias diferentes e na primeira parte implementei spring data com servidor RMI em java entao o cliente foi escrito tambem em java com a interface do RMI.

Nesta segunda parte irei usar uma abordagem diferente.

Neste trabalho não temos preocupação com aspecto visual, os parametros de configuração devem ser exteriores a propria app e a aplicação deve ser compativel com a plataforma alunos.di.uevora.pt, e a BD deve ser PostgresQL.

### Modelo de dados

1. [] User
    - UserID (chave primária)
    - Username
    - Email
    - Password
    - Tipo (administrador|normal)

2. [] Artista:
    - ArtistID (chave primária)
    - Nome
    - Estado (não aprovado|aprovado)
    - Lista de Localizações (contendo latitude e longitude)
    - Rating (opcional)

3. [] Donativo:
    - DonativoID (chave primária)
    - Data
    - Valor
    - ArtistaID (chave estrangeira referenciando Artista)
    - UserID (chave estrangeira referenciando User)

### Operações para Cliente Geral

1. [] Registar-se no Sistema:
    - Inserir um novo usuário com username, email e password.
    - Verificar duplicatas (username ou email).

2. [] Autenticar-se:
    - Verificar username e password para autenticação.

3. [] Pedido de Registo de Novo Artista:
    - Inserir novo artista com estado não aprovado.
    - Adicionar localização (se necessário).

4. [] Listar Artistas:
    - Listar artistas com opção de filtros (localização e arte).

5. [] Listar Localizações de Artistas:
    - Listar localizações onde existem artistas a atuar.

6. [] Listar Atuações Anteriores de um Artista:
    - Para um artista identificado por ArtistID, listar datas e localizações.

7. [] Listar Próxima Atuação de um Artista:
    - Para um artista identificado por ArtistID, listar próxima atuação.

8. [] Enviar Donativo a um Artista:
    - Inserir um novo donativo indicando valor, artista e utilizador.

9. [] Listar Donativos Recebidos por um Artista:
    - Para um artista identificado por ArtistID, listar donativos recebidos.

10. [] (Bónus) Dar Classificação a um Artista:
    - Atribuir uma classificação (rating) a um artista.

11. [] (Bónus) Ver Rating de Cada Artista:
    - Incluir rating na listagem de artistas.


### Operações para Cliente Administrador

1. [] Autenticar-se como Administrador:
    - Verificar username, password e tipo de administrador.

2. [] Dar Permissão de Administrador a um User:
    - Atualizar o tipo de um usuário para administrador.

3. [] Listar Artistas por Estado:
    - Listar artistas com base no estado (aprovado ou não).

4. [] Aprovar um Artista:
    - Atualizar o estado do artista para aprovado.

5. [] Consultar e Alterar Informações de um Artista:
    - Consultar informações de um artista.
    - Permitir alterações relevantes.

6. [] Todas as Operações Permitidas pelo Cliente Geral.


### Operações para o Servidor (Bónus):

1. [] Suporte para Redundância/Replicação:
    - Configurar replicação assíncrona para tolerar falhas.

2. [] Mecanismo Publish/Subscribe:
    - Implementar um mecanismo de publish/subscribe para notificar sobre novos artistas ou atuações em tempo real.

## Inicio

Irei enfrentar este projecto por fases, sendo ele feito de micro serviços. Irei ter 1 servido de auth com duas base de dados 1 Master e 1 Slave que sera usado para Autenticação.

