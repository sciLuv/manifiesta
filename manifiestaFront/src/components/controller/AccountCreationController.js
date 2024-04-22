import React, { useState } from 'react';
import AccountCreation from '../view/AccountCreationView';
import { URI_BASE } from '../../env';

export default function AccountCreationController(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [mail, setMail] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [showSuccessMessage, setShowSuccessMessage] = useState('d-none');
    const [showErrorMessage, setShowErrorMessage] = useState('d-none');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleMailChange = (event) => {
        setMail(event.target.value);
    };

    const handleFormSubmit = () => {

        console.log('Creating account...');
    
        const requestData = {
            username: username,
            password: password,
            mail: mail
        };
    
        fetch(URI_BASE + '/public/createAccount', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData) // Convertit l'objet JavaScript en chaîne JSON
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Account created:', data);
            if((data.responseKC == "User exists with same email")|| (data.responseKC == "User exists with same username")) {
                setErrorMessage("Un utilisateur existe déjà avec ce nom d'utilisateur ou cet email");
                setShowErrorMessage('');
                setShowSuccessMessage('d-none');
            } else if((data.responseKC == "User created") && (data.responseDB == "User created")) {
                //ici ca renvoie l'utilisateur vers la page d'accueil
                setErrorMessage('');
                setShowErrorMessage('d-none');
                setShowSuccessMessage('');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to create account: ' + error.message);
        });

    };
    

    return (
        <AccountCreation
            username={username} 
            password={password} 
            mail={mail}
            errorMessage={errorMessage}
            showSuccessMessage={showSuccessMessage}
            showErrorMessage={showErrorMessage}
            onUsernameChange={handleUsernameChange}
            onMailChange={handleMailChange}
            onPasswordChange={handlePasswordChange} 
            onSubmit={handleFormSubmit} 
        />
    );
};
