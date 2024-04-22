import React from 'react';

const AccountCreation = ({ username, mail, password, errorMessage, showSuccessMessage, showErrorMessage, onUsernameChange, onMailChange, onPasswordChange, onSubmit }) => {
    

    return (
        <div className="container mt-3">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h2 className="card-title">Créer un compte</h2>
                            <div>
                                <div className="form-group">
                                    <label htmlFor="username">Pseudonyme:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="username"
                                        value={username}
                                        onChange={onUsernameChange}
                                        required
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="email">Mail:</label>
                                    <input
                                        type="email"
                                        className="form-control"
                                        id="email"
                                        value={mail}
                                        onChange={onMailChange}
                                        required
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="password">Mot de passe:</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        id="password"
                                        value={password}
                                        onChange={onPasswordChange}
                                        required
                                    />
                                </div>
                                <button onClick={onSubmit} className="btn btn-primary">Création du compte</button>
                            </div>
                        </div>
                        <div className={`error-message text-danger text-center mb-3 ${showErrorMessage}`}>
                            {errorMessage}
                        </div>
                        <div className={`success-message text-success text-center mb-3 ${showSuccessMessage}`}>
                            Création du compte réussie, <a href="/login">connectez-vous</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AccountCreation;
