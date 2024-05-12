import React from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, FormGroup, FormControl, FormCheck } from 'react-bootstrap';

const JoinSessionView = ({
    accessCode,
    password,
    includePassword,
    onAccessCodeChange,
    onPasswordChange,
    onIncludePasswordChange,
    onJoinSessionSubmit,
    showErrorMessage
}) => {
    return (
        <Container className="mt-3">
            <Row className="justify-content-center">
                <Col md={6}>
                    <Card className="shadow bg-dark text-light">
                        <Card.Body className='pb-1'>
                            <Card.Title>Rejoindre une Session</Card.Title>
                            <Form onSubmit={onJoinSessionSubmit}>
                                <FormGroup className="mb-3">
                                    <Form.Label htmlFor="access-code">Code d'accès:</Form.Label>
                                    <FormControl
                                        type="text"
                                        id="access-code"
                                        value={accessCode}
                                        onChange={onAccessCodeChange}
                                        required
                                    />
                                </FormGroup>
                                <FormGroup className="mb-3">
                                    <FormCheck 
                                        type="checkbox" 
                                        label="Inclure un mot de passe"
                                        checked={includePassword}
                                        onChange={onIncludePasswordChange}
                                    />
                                </FormGroup>
                                {includePassword && (
                                    <FormGroup className="mb-3">
                                        <Form.Label htmlFor="session-password">Mot de passe:</Form.Label>
                                        <FormControl
                                            type="password"
                                            id="session-password"
                                            value={password}
                                            onChange={onPasswordChange}
                                        />
                                    </FormGroup>
                                )}
                                <Button variant="primary" type="submit" className='valid-button mb-3'>Rejoindre</Button>
                            </Form>
                            <Card.Footer className={`text-danger text-center ${showErrorMessage ? "" : "d-none"}`}>
                                Il y a eu une erreur lors de la connexion à la session. vérifier le code d'accès et le mot de passe.
                            </Card.Footer>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default JoinSessionView;
