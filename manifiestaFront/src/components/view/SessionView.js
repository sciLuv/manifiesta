import React from "react";
import { Container, Row, Col, Card, Button, ProgressBar } from "react-bootstrap";
import MusicPlayer from "./MusicPlayer";


const SessionView = (props) => {
    // Données simulées, remplacer par les props si les données sont passées du parent
    const data = props.stringJson;

    return (
        <Container fluid className="session-view ">
            <Row className="justify-content-center align-items-start full-height ">
                <Col className="">
                    <Card className="text-center dark-card bg-dark dflex-session">
                        <div className="title-and-image-session">
                            <Card.Img variant="top" src={data.currentlyPlayingMusic.url_img} className="album-image" />
                            <Card.Title className="text-orange mt-3">{data.currentlyPlayingMusic.name}</Card.Title>
                            <Card.Text className=" pb-3">
                                    <span className="text-orange">Artiste : </span>
                                    {data.currentlyPlayingMusic.artist}
                                    <span className="text-orange">  Album : </span>
                                    {data.currentlyPlayingMusic.album}
                            </Card.Text>
                                <MusicPlayer
                                    currentSongProgress={data.currentMusicInformation.progressMs}
                                    duration={data.currentlyPlayingMusic.duration}
                                    beginhour={data.currentMusicInformation.timeStamp}
                                />
                        </div>
                        <Card.Body>

                            <div>
                                <h3 className="mb-3">Sélectionnez la prochaine musique :</h3>
                                {data.musics.map((music, index) => (
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
