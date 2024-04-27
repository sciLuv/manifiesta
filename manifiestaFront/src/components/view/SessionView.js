import React from "react";
import { Container, Row, Col, Card, Button, ProgressBar } from "react-bootstrap";
import MusicPlayer from "./MusicPlayer";
import SessionNavView from "./SessionNavView";


const SessionView = (props) => {
    // Données simulées, remplacer par les props si les données sont passées du parent
    const data = props.stringJson;

    console.log(data);

    return (
        <Container fluid className="session-view">
            <Row className="justify-content-center align-items-start full-height ">
                <Col className="">
                    <SessionNavView 
                        userCount={12} 
                        playedMusicCount={5} 
                        userRole={"user"}
                    />
                    <Card className="text-center dark-card bg-dark dflex-session mt-3">
                        <div className="title-and-image-session">
                            <Card.Img variant="top" src={data.musicCurrentlyPlayed.imageUrl} className="album-image" />
                            <Card.Title className="text-orange mt-3">{data.musicCurrentlyPlayed.name}</Card.Title>
                            <Card.Text className=" pb-3">
                                    <span className="text-orange">Artiste : </span>
                                    {data.musicCurrentlyPlayed.artist}
                                    <span className="text-orange">  Album : </span>
                                    {data.musicCurrentlyPlayed.album}
                            </Card.Text>
                                <MusicPlayer
                                    currentSongProgress={data.musicCurrentlyPlayed.progressMs}
                                    duration={data.musicCurrentlyPlayed.durationMs}
                                />
                        </div>
                        <Card.Body>

                            <div>
                                <h3 className="mb-3">Sélectionnez la prochaine musique :</h3>
                                {data.musics.musics.map((music, index) => (
                                    <Button
                                        key={index}
                                        className="song-button m-3"
                                        onClick={() => console.log(`Playing ${music.name}`)}
                                    >
                                        {music.name + " - " + music.artist}
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
