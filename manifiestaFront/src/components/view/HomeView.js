import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import { Route, Routes, useNavigate, Link  } from 'react-router-dom';
import logo from '../../img/logo_2.png';
import UserImg from '../../img/user.svg';
import MusicImg from '../../img/music.svg';
import SessionParameterView from './SessionParameterView';

const HomeView = (props) => {

    const navigate = useNavigate();
    return (
        <Container fluid style={{ minHeight: '100vh', padding: '2rem' }}>
            <Row className="justify-content-center">
                <Col md={8} lg={6} className="text-center">
                {props.role === "user" ? (
                    <div>
                        <h2 className="text-orange mb-4">Bienvenue {props.user} !</h2>
                        {!props.userHasSession ? (
                            <Card className="h-100 text-center shadow bg-dark text-light mb-5">
                            <Card.Body className="card-with-image">
                                <div>
                                <Card.Title>Créer une session d'écoute</Card.Title>
                                <Card.Text>Pour choisir les musiques que vous allez écouter avec vos amis</Card.Text>
                                
                                <Button variant="primary" as={Link} to="/create-new-session" className='valid-button'>Démarrer une session d'écoute</Button>
                                </div>
                                <img src={logo} alt="logo" className='m-2'/>
                            </Card.Body>
                        </Card>
                        ) : (
                            <>
                                <h4 className='text-light mb-4'>Vous avez crée une session d'écoute toujours en cours </h4>
                                <Card className="h-100 text-center shadow bg-dark text-light mb-5">
                                    <Card.Body className="card-with-image">
                                        <div>
                                            <span className='mb-2'>
                                                <img src={UserImg} alt="Menu" style={{ width: '20px', height: '20px' }} />  {props.userSession.numberOfParticipants} 
                                                <img src={MusicImg} alt="Menu" style={{ width: '20px', height: '20px', marginLeft : '30px' }} /> {props.userSession.numberOfPollturns}
                                            </span>
                                            <div className='mt-2 '>
                                                <span className='text-orange mr-2'>Code d'accès : </span> {props.userSession.qrcode}
                                                { props.userSession.password !== "" ? (
                                                    <><br/><span className='text-orange mr-2 mt-2'>Mot de passe : </span> {props.userSession.password}</>) : null
                                                }
                                            </div>
                                        
                                            <Button  className="m-3 valid-button" variant="primary" onClick={
                                                () => {
                                                    props.handleJoinSession(props.userSession.password, props.userSession.qrcode)
                                                }
                                            }>Rejoindre votre session d'écoute</Button>
                                        </div>
                                        <img src={logo} alt="logo" className='m-2'/>
                                    </Card.Body>
                                </Card>
                            </>
                        )}
                        <h4 className='text-light mb-4'>Les sessions d'écoute en cours auxquelles vous participez</h4>
                        {props.userParticipateToSession ? (
                                    props.userParticipateSession.map((session, index) => (
                                        <Card key={index} className="h-100 text-center shadow bg-dark text-light">
                                            <Card.Body className="card-with-image">
                                                <div>
                                                <div className='mt-2 '>
                                                <span className='text-orange mb-2'>Session crée par : </span> {session.nameOfUserCreator}
                                                <div className='mt-2'>
                                                    <span className='mb-2'>
                                                            <img src={UserImg} alt="Participants" style={{ width: '20px', height: '20px' }} />  {session.numberOfParticipants} 
                                                            <img src={MusicImg} alt="Turns" style={{ width: '20px', height: '20px', marginLeft : '30px' }} /> {session.numberOfPollturns}
                                                    </span>
                                                </div>
                                                </div>
                                                    
                                                    <Button className="m-3" variant="primary" onClick={
                                                        () => {
                                                            props.handleJoinSession(session.password, session.qrcode)
                                                        }
                                                    }>Rejoindre cette session d'écoute</Button>
                                                </div>
                                                <img src={logo} alt="logo" className='m-2'/>
                                            </Card.Body>
                                        </Card>
                                    ))
                        ) : (
                            <span className='text-light italic'>Vous ne participez à aucune session d'écoute</span>
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
