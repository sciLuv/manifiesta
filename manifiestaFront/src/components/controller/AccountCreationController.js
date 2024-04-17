import React, { useState } from 'react';
import AccountCreation from '../view/AccountCreationView';
import { URI_BASE } from '../../env';

export default function AccountCreationController(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handleFormSubmit = () => {

        console.log('Creating account...');
    
        const requestData = {
            username: username,
            password: password,
            email: email
        };
    
        fetch(URI_BASE + '/createAccount', {
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
            email={email}
            onUsernameChange={handleUsernameChange}
            onEmailChange={handleEmailChange}
            onPasswordChange={handlePasswordChange} 
            onSubmit={handleFormSubmit} 
        />
    );
};