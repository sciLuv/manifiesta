import React from 'react';
import { Container, Row, Col, Card, Form, Button, Alert } from 'react-bootstrap';

const LoginView = ({ username, password, errorMessage, showErrorMessage, onUsernameChange, onPasswordChange, onLoginSubmit }) => {
    
    const handleSubmit = (event) => {
        event.preventDefault();
        onLoginSubmit();
    };

    return (
        <Container className="mt-3">
            <Row className="justify-content-center">
                <Col md={6}>
                    <Card className="text-center shadow bg-dark text-light">
                        <Card.Body>
                            <Card.Title>Connexion</Card.Title>
                            <Form onSubmit={handleSubmit}>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="login-username">Pseudonyme:</Form.Label>
                                    <Form.Control
                                        type="text"
                                        id="login-username"
                                        value={username}
                                        onChange={onUsernameChange}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="login-password">Mot de passe:</Form.Label>
                                    <Form.Control
                                        type="password"
                                        id="login-password"
                                        value={password}
                                        onChange={onPasswordChange}
                                        required
                                    />
                                </Form.Group>
                                <Button variant="primary" type="submit" className='valid-button mt-2'>Se connecter</Button>
                            </Form>
                        </Card.Body>
                        {errorMessage && (
                            <Card.Footer className={`text-danger ${showErrorMessage ? "" : "d-none"}`}>
                                {errorMessage}
                            </Card.Footer>
                        )}
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default LoginView;
