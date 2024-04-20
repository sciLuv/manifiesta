
# Manifiesta
 
An application enabling the interactive sharing of Spotify playlists, between users spatially present together, while integrating a voting system to determine the next music to listen to, in order to enhance general musical appreciation and foster interpersonal interaction in a festive setting.

## How To Install :

  

### Spotify

  

### Security

*localhost:8080*

Manifiesta use keycloak framework and a MySQL database for user's authentification, role's autorisation and data encryption. First download Keycloak.

**MySQL configuration to be used with Keycloak :** 

    //We must use InnoDB engine for MySQL
    SET GLOBAL default_storage_engine = 'InnoDB';

    //To verify if InnoDB is apply
    SHOW GLOBAL variables like "default_storage%"; 

    //To avoid some behavior doesn't work with Keycloak
    SET SESSION sql_generate_invisible_primary_key = OFF;

    //to verify if it's apply
    SHOW VARIABLES LIKE 'sql_generate_invisible_primary_key';


    //Create a specific MySQL user for keycloak
    CREATE USER 'name'@'%' IDENTIFIED BY 'password';

    //Create a specific DB for keycloak
    CREATE DATABASE dbname;

    //To add capacity to interact with the Db to the keycloak user
    GRANT ALL PRIVILEGES ON dbname.* TO 'name'@'%';

**Configure the keycloak.conf file (in conf folder), to allow it to use MySQL DB**
1/ remove all thing already write in the keycloak.conf file
2/ write this instead: 

*The database vendor.
db=mysql
The username of the database user.
db-username=[name-of-database]
The password of the database user.
db-password=[password]
The full database JDBC URL. If not provided default URL is set based on the selected database vendor.
db-url=jdbc:mysql://localhost:3306/dbname
characterEncoding=UTF-8*

**Setting keycloak administrator space (in localhost:8080 by default)**
1 - create an admin and its password to access to the keycloak administrator space panel
2 - create a realm manifiesta
2 - create an admin user for the new realm, with validate password and mail, and add him the realm-admin realmrole
3 - create a new client, call it "manifiesta", and select "client authentification", "Authorization" and "service accounts roles" in "capability config"
4 - in the new client creation write "http://<your-host>", "http://<your-host>", "http://<your-host>/\*" , "http://<your-host>", "\*"
5 - in this realm, find the "admin-cli" client and select "client authentification" and "Authorization" in "capability config"
6 - After that, we can go in crendential and find the secret_key, necessary for use keycloak admin API
7 - create two realm-roles, "user", and "guest"
8 - create two client-roles for manifiesta client "client_user" and "client_guest"
9 - associate "user" with "client_user" and "guest" with "client_guest" in "add associated roles" of each client-role created
10 - go to "real setting" in "keys" and copy the public key of rs256

**Default values of variables creates in this process**

MySQL user : keycloakAdmin
MySQL user password : g7PUOC-hqD-&
MySQL database keycloak name : keycloakManifiesta

keycloak administrator space name : admin
keycloak adminstrator space password : admin  

### Backend

*localhost:8180*

The backend of the application use Spring, SpringBoot and a MySQL Database

    //Create a specific MySQL user for keycloak
    CREATE USER 'name'@'%' IDENTIFIED BY 'password';

    //Create a specific DB for keycloak
    CREATE DATABASE dbname;

	Create a specific DB for manifiesta application
    CREATE DATABASE dbname;
MySQL database manifiesta application name : manifiesta
  

### FrontEnd

*localhost:3000*

**to add all nodeJS dependance there are 3 commands to do :**

npm install --save-dev webpack webpack-cli webpack-dev-server html-webpack-plugin style-loader css-loader file-loader bootstrap react-bootstrap nanoid sass react-router-dom react-router sockjs

npm install --save-dev @babel/core @babel/preset-env @babel/preset-react babel-loader

npm install sass-loader css-loader style-loader --save-dev