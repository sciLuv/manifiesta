import React, { useState } from 'react';
import LoginView from '../view/LoginView';

const LoginController = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [showErrorMessage, setShowErrorMessage] = useState(false);

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleLoginSubmit = async () => {
        // Initialisation des messages d'erreur
        setShowErrorMessage(false);
        setErrorMessage('');

        try {
            const response = await fetch('http://localhost:8180/public/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error('Erreur de connexion');
            }

            const data = await response.json();
            
            // Traitez ici les données de réponse, comme le stockage du token
            console.log(data);
            const customHeader = response.headers.get('access-Token');
            console.log(customHeader);
            console.log("test");

            // Rediriger l'utilisateur ou effectuer d'autres actions de connexion réussie
        } catch (error) {
            setShowErrorMessage(true);
            setErrorMessage(error.message || 'Une erreur est survenue lors de la connexion');
        }
    };

    return (
        <LoginView
            username={username}
            password={password}
            errorMessage={errorMessage}
            showErrorMessage={showErrorMessage}
            onUsernameChange={handleUsernameChange}
            onPasswordChange={handlePasswordChange}
            onLoginSubmit={handleLoginSubmit}
        />
    );
};

export default LoginController;
