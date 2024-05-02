import  React from  'react' ;
import  { Container, Row, Col } from  'react-bootstrap' ;
import Xmark from '../../img/xmark.svg';

const SessionOverlayView = ({
    setOverlayIsOpen, sessionCode, sessionPassword, 
    listOfParticipants, showListParticipants, showcodeAndPassword, 
    setShowListParticipants, setShowcodeAndPassword
    }) => {
    return (
        <div className="overlay" style={{
            position: 'absolute',  // or 'absolute' if you want it relative to the nearest positioned ancestor
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgb(0, 0, 0)',  // Semi-transparent black
            zIndex: 1050,  // Higher than anything else in the container
        }}>
            <img src={Xmark} onClick={() => {
                setOverlayIsOpen(false);
                setShowListParticipants(false);
                setShowcodeAndPassword(false);
            }} alt="back to the session main interface" className="xmark" style={{ width: '40px', height: '40px' }} /> 
            <Container>
                <Row className="content-info-session">
                    <Col xs="auto">
                        <img src={Xmark} alt="Retour à l'interface principale de la session" className="xmark" />
                    </Col>
                    <Col xs={12}>
                        {showcodeAndPassword ? 
                            <div>
                            <h4 className="text-white">Code de la session</h4>
                            <p className="text-white">{sessionCode}</p>
                            {sessionPassword === "" ? null :
                                <>
                                    <h4 className="text-white">Mot de passe</h4>
                                    <p className="text-white">{sessionPassword}</p>
                                </>
                            }
                            </div> 
                            : null
                        }
                        {showListParticipants ?
                            <div>
                                <h4 className="text-white">Liste des participants</h4>
                                {listOfParticipants.length > 0 ? (
                                    <ul className="list-unstyled">
                                        {listOfParticipants.map((participant, index) => (
                                            <li key={index} className="text-white">
                                                {participant.username} {participant.guest ? '(Invité)' : '(Membre)'}
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <p className="text-white">Aucun participant n'est connecté.</p>
                                )}
                            </div>
                            : null
                        }
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default SessionOverlayView;