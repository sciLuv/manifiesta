import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import { Route, Routes, useNavigate, Link  } from 'react-router-dom';
import logo from '../../img/logo_2.png';
import SessionParameterView from './SessionParameterView';

const HomeView = (props) => {

    const navigate = useNavigate();
    return (
        <Container fluid style={{ minHeight: '100vh', padding: '2rem' }}>
            <Row className="justify-content-center">
                <Col md={8} lg={6} className="text-center">
                {props.role === "user" ? (
                    <div>
                        <h2 className="text-light mb-4">Bienvenue {props.user} !</h2>
                        {!props.UserHasSession ? (
                            <Card className="h-100 text-center shadow bg-dark text-light">
                            <Card.Body className="card-with-image">
                                <div>
                                <Card.Title>Créer une session d'écoute</Card.Title>
                                <Card.Text>Pour écouter et choisir les musiques que vous allez écouté avec vos amis</Card.Text>
                                
                                <Button variant="primary" as={Link} to="/create-new-session">Démarrer une session d'écoute</Button>
                                </div>
                                <img src={logo} alt="logo" className='m-2'/>
                            </Card.Body>
                        </Card>
                        ) : (
                            <>
                                <h4 className='text-light'>Votre session d'écoute est en cours </h4>
                                <Card className="h-100 text-center shadow bg-dark text-light">
                                    <Card.Body className="card-with-image">
                                        <Button variant="primary" as={Link} to="/join-session">Rejoindre votre session d'écoute</Button>
                                        <img src={logo} alt="logo" className='m-2'/>
                                    </Card.Body>
                                </Card>
                            </>
                        )}
                        <h4 className='text-light'>Les session d'écoute en cours auquelles vous participez</h4>
                        {props.UserParticipateToSession ? (
                            <Card className="h-100 text-center shadow bg-dark text-light">
                                <Card.Body className="card-with-image">
                                    <Button variant="primary" as={Link} to="/join-session">Retourner sur cette session d'écoute</Button>
                                    <img src={logo} alt="logo" className='m-2'/>
                                </Card.Body>
                            </Card>
                        ) : (
                            <span className='text-light'>Vous ne participez à aucune session d'écoute</span>
                        )}
                    </div>
                ) : (
                    <div>
                        <img src={logo} alt="logo" style={{ width: '100%', maxWidth: '200px' }} className='m-2'/>
                        <h2 className="text-light">Bienvenue sur manifiesta !</h2>
                        <p className="text-light">
                            <a href="/login">connectez-vous</a> pour accéder à toutes les fonctionnalités du site.<br/>
                            Si vous n'avez pas de compte, vous pouvez en <a href="/account">créer un</a>.
                            vous pouvez aussi <a href="/join-session-guest">rejoindre une session en tant qu'invité</a>.
                        </p>
                    </div>
                )}
                </Col>
            </Row>
        </Container>
    );
};

export default HomeView;
