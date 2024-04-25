import React from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";


const SessionView = (props) => {
    const songs = [
        { title: 'Song 1' },
        { title: 'Song 2' },
        { title: 'Song 3' },
        { title: 'Song 4' },
    ];

    return (
        <Container fluid className="session-view">
            <Row className="justify-content-center align-items-center full-height">
                <Col xs={12} md={6} className="d-flex justify-content-center">
                    <Card className="text-center dark-card">
                        <Card.Img variant="top" src="album-cover.jpg" className="album-image" />
                        <Card.Body>
                            <Card.Title className="text-orange">Prochaine Chanson</Card.Title>
                            <div className="d-grid gap-2">
                                {songs.map((song, index) => (
                                    <Button
                                        key={index}
                                        className="song-button"
                                        onClick={() => console.log(`Playing ${song.title}`)}
                                    >
                                        {song.title}
                                    </Button>
                                ))}
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>  
        </Container>
    )
}

export default SessionView;
