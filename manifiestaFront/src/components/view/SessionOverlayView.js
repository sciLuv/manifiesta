import  React from  'react' ;
import  { Container, Row, Col } from  'react-bootstrap' ;
import Xmark from '../../img/xmark.svg';

const SessionOverlayView = () => {
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
            <img src={Xmark}  alt="back to the session main interface" className="xmark" style={{ width: '40px', height: '40px' }} /> 
            <Container>
                <Row className="content-info-session">
                    <Col xs="auto">
                        <img src={Xmark} alt="Retour à l'interface principale de la session" className="xmark" />
                    </Col>
                    <Col xs={12}>
                        <h4 className="text-white">Informations supplémentaires</h4>
                        <p className="text-white">Ici, tu peux ajouter d'autres éléments d'information...</p>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default SessionOverlayView;