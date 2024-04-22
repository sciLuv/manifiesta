import React from 'react';

const LoginView = ({ username, password, errorMessage, showErrorMessage, onUsernameChange, onPasswordChange, onLoginSubmit }) => {
    return (
        <div className="container mt-3">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h2 className="card-title">Connexion</h2>
                            <div>
                                <div className="form-group">
                                    <label htmlFor="login-username">Pseudonyme:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="login-username"
                                        value={username}
                                        onChange={onUsernameChange}
                                        required
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="login-password">Mot de passe:</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        id="login-password"
                                        value={password}
                                        onChange={onPasswordChange}
                                        required
                                    />
                                </div>
                                <button onClick={onLoginSubmit} className="btn btn-primary">Se connecter</button>
                            </div>
                        </div>
                        {errorMessage && (
                            <div className={`error-message text-danger text-center mb-3 ${showErrorMessage ? "" : "d-none"}`}>
                                {errorMessage}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginView;
