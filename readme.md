# Manifiesta

An application enabling the interactive sharing of Spotify playlists between users spatially present together, integrating a voting system to determine the next song to play. This application enhances general musical appreciation and fosters interpersonal interaction in a festive setting.

## How To Install

### Spotify

First, you'll need to create a Spotify Developer account. Once your account is set up, navigate to the Dashboard and create a new application. This will generate a Client ID and Client Secret that you can use to integrate Spotify's API into your project.

Be sure to add the appropriate Redirect URIs in your app settings. These URIs are crucial because they allow Spotify to securely redirect users back to your front-end application with the necessary authorization information.

### Security

**Localhost:8080**
 
Manifiesta uses the Keycloak framework and a MySQL database for user authentication, role authorization, and data encryption. Below are the steps to set up Keycloak and MySQL.

#### MySQL Configuration for Keycloak

1. Set the default storage engine to InnoDB:
    ```sql
    SET GLOBAL default_storage_engine = 'InnoDB';
    ```

2. Verify that InnoDB is applied:
    ```sql
    SHOW GLOBAL VARIABLES LIKE "default_storage%";
    ```

3. Disable invisible primary key generation (needed for Keycloak):
    ```sql
    SET SESSION sql_generate_invisible_primary_key = OFF;
    ```

4. Verify that the setting is applied:
    ```sql
    SHOW VARIABLES LIKE 'sql_generate_invisible_primary_key';
    ```

5. Create a specific MySQL user for Keycloak:
    ```sql
    CREATE USER 'name'@'%' IDENTIFIED BY 'password';
    ```

6. Create a specific database for Keycloak:
    ```sql
    CREATE DATABASE dbname;
    ```

7. Grant the necessary privileges to the Keycloak user:
    ```sql
    GRANT ALL PRIVILEGES ON dbname.* TO 'name'@'%';
    ```

#### Configure the `keycloak.conf` File

1. Clear the contents of the `keycloak.conf` file (located in the `conf` folder).
2. Add the following configuration:

    ```
    # The database vendor
    db=mysql
    
    # The username of the database user
    db-username=[name-of-database]
    
    # The password of the database user
    db-password=[password]
    
    # The full database JDBC URL. If not provided, a default URL is set based on the selected database vendor.
    db-url=jdbc:mysql://localhost:3306/dbname?characterEncoding=UTF-8
    ```

#### Setting up the Keycloak Administrator Space

1. Create an admin user and password to access the Keycloak administrator panel.
2. Create a realm named `manifiesta`.
3. Create an admin user for the new realm, validate the password and email, and assign the `realm-admin` realm role.
4. Create a new client named `manifiesta` and enable "Client Authentication," "Authorization," and "Service Accounts Roles" in the capability config.
5. Set valid redirect URIs for the client (e.g., `http://<your-host>` and `http://<your-host>/*`).
6. In the `admin-cli` client, enable "Client Authentication" and "Authorization."
7. Retrieve the `secret_key` from the "Credentials" tab, necessary for using the Keycloak Admin API.
8. Create two realm roles: `user` and `guest`.
9. Create two client roles for the `manifiesta` client: `client_user` and `client_guest`.
10. Associate `user` with `client_user` and `guest` with `client_guest`.
11. Go to "Realm Settings" > "Keys" and copy the RS256 public key.

#### Default Values for Variables

- **MySQL User**: `keycloakAdmin`
- **MySQL User Password**: `g7PUOC-hqD-&`
- **MySQL Database Keycloak Name**: `keycloakManifiesta`
- **Keycloak Administrator Name**: `admin`
- **Keycloak Administrator Password**: `admin`

### Backend

**Localhost:8180**

The backend of the application uses Spring, Spring Boot, and a MySQL Database.

1. Create a MySQL user for the backend:
    ```sql
    CREATE USER 'name'@'%' IDENTIFIED BY 'password';
    ```

2. Create a specific database for the Manifiesta application:
    ```sql
    CREATE DATABASE manifiesta;
    ```

### Frontend

**Localhost:3000**

To install all Node.js dependencies, run the following commands:

1. Install Webpack and related tools:
    ```bash
    npm install --save-dev webpack webpack-cli webpack-dev-server html-webpack-plugin style-loader css-loader file-loader bootstrap react-bootstrap nanoid sass react-router-dom react-router sockjs
    ```

2. Install Babel for transpiling:
    ```bash
    npm install --save-dev @babel/core @babel/preset-env @babel/preset-react babel-loader
    ```

3. Install Sass and CSS loaders:
    ```bash
    npm install sass-loader css-loader style-loader --save-dev
    ```
