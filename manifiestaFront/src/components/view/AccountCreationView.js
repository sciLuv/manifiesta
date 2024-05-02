import React from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';

const AccountCreation = ({
  username, 
  mail, 
  password, 
  errorMessage, 
  showSuccessMessage, 
  showErrorMessage, 
  onUsernameChange, 
  onMailChange, 
  onPasswordChange, 
  onSubmit 
}) => {
    
    // Handler pour le formulaire qui empêche la soumission par défaut
    const handleSubmit = (event) => {
        event.preventDefault();
        onSubmit();
    };

    return (
        <Container className="mt-3">
            <Row className="justify-content-center">
                <Col md={6}>
                    <Card className="text-center shadow bg-dark text-light">
                        <Card.Body>
                            <Card.Title>Créer un compte</Card.Title>
                            <Form onSubmit={handleSubmit}>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="username">Pseudonyme:</Form.Label>
                                    <Form.Control
                                        type="text"
                                        id="username"
                                        value={username}
                                        onChange={onUsernameChange}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="email">Mail:</Form.Label>
                                    <Form.Control
                                        type="email"
                                        id="email"
                                        value={mail}
                                        onChange={onMailChange}
                                        required
                                    />
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="password">Mot de passe:</Form.Label>
                                    <Form.Control
                                        type="password"
                                        id="password"
                                        value={password}
                                        onChange={onPasswordChange}
                                        required
                                    />
                                </Form.Group>
                                <Button variant="primary" type="submit" className='valid-button mt-3'>Création du compte</Button>
                            </Form>
                        </Card.Body>
                        <Card.Footer className="text-muted">
                            <div className={`text-danger ${showErrorMessage}`}>
                                {errorMessage}
                            </div>
                            <div className={`text-success ${showSuccessMessage}`}>
                                Création du compte réussie, <a href="/login" className="text-light">connectez-vous</a>
                            </div>
                        </Card.Footer>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default AccountCreation;
