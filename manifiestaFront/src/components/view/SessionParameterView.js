import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert } from "react-bootstrap";


const SessionParameterView = ({ 
    passwordSession, 
    errorMessage, 
    showErrorMessage, 
    onUsernameChange, 
    onPasswordSessionChange, 
    onLoginSubmit,
    qrCodeType,
    passwordOrNot,
    setQrCodeType,
    setPasswordOrNot,
    isSpotifyConnected,
    setIsSpotifyConnected,
    changeCheckBoxPassword,
    handleAuthorization,
    spotifyRefreshToken,
    setSpotifyTokenRefresh,
    spotifyToken,
    setSpotifyToken,
    handleNewSession,
    handleSongsNumberChange,
    handleMusicalStylesChange,
    setIsQRCodeGlobal
    }) => {   

    // État local pour l'option sélectionnée
    const [selectedOption, setSelectedOption] = useState("QR code de session");

    // Gestionnaire pour le changement de sélection dans le menu déroulant
    const handleSelectChange = (event) => {
            const selectedValue = event.target.value;
            console.log(selectedValue);
            setSelectedOption(selectedValue);
            if(event.target.value == "QR code de session"){
                console.log("false");
                setIsQRCodeGlobal(false);
            } else {
                console.log("true");
                setIsQRCodeGlobal(true);
            }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onLoginSubmit();
    };

    return (
        <Container>
          <Row>
            <Col>
            <div className="flex">
            {isSpotifyConnected ? (
                <>
                    <Card className="mt-4 text-light spotify-autorisation-card">
                        <Card.Body>
                            <Card.Title>Manifiesta peut utiliser votre compte Spotify 👍</Card.Title>
                        </Card.Body>
                    </Card>
                </>
            ) : (
                <Card className="mt-5 text-light spotify-autorisation-card">
                    <Card.Body>
                        <Card.Title>Connectez-vous à Spotify</Card.Title>
                        <Card.Text>
                            Pour commencer votre session il faut autoriser Manifiesta à utiliser Spotify.
                        </Card.Text>
                        <Button variant="primary" className="valid-button" onClick={handleAuthorization}>
                            Autoriser Manifiesta à utiliser Spotify 
                        </Button>
                    </Card.Body>
                </Card>
            )}
            {isSpotifyConnected ?
                //ici on crée un formulaire pour que l'utilisateur puisse choisir les paramètres de sa session
                <Card className="mt-5 mb-5 w-75 shadow bg-dark text-light">
                    <Card.Body >
                    <Form>
                        {showErrorMessage && <Alert variant="danger">{errorMessage}</Alert>}
        
                        <Form.Group className="mb-3" controlId="qrCodeType">
                        <Form.Label>Quel type de QRcode voulez vous utiliser ?</Form.Label>
                        <Form.Control as="select" value={selectedOption} onChange={handleSelectChange}>
                                                {["QR code de session", "QR code d'utilisateur"].map((option) => (
                                                    <option key={option} value={option}>
                                                        {option}
                                                    </option>
                                                ))}
                        </Form.Control>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="passwordOrNot">
                        <Form.Check 
                            type="checkbox"
                            label="utiliser un mot de passe de session"
                            onChange={changeCheckBoxPassword}
                        />
                        </Form.Group>
                        {passwordOrNot ? (
                            <Form.Group className="mb-3" controlId="passwordSession">
                            <Form.Label>Mot de passe de la session</Form.Label>
                            <Form.Control
                            type="password"
                            value={passwordSession}
                            onChange={onPasswordSessionChange}
                            />
                        </Form.Group>
                        ) : <> </>}
                        
        
                        <Form.Group className="mb-3" controlId="numberOfSongs">
                        <Form.Label>Nombre de musiques par vote</Form.Label>
                        <Form.Control as="select" onClick={handleSongsNumberChange}>
                            {[2, 3, 4, 5, 6].map((number) => (
                            <option key={number} value={number}>
                                {number}
                            </option>
                            ))}
                        </Form.Control>
                        </Form.Group>

                        <Button variant="primary" className="valid-button" onClick={handleNewSession}>
                             Commencer la session !
                        </Button>
                    </Form>
                    </Card.Body>
                </Card>
                
                : <> </>
            }
            </div>
            </Col>
          </Row>
        </Container>
      );
}


export default SessionParameterView;