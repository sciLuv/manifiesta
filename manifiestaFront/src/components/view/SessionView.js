import React from "react";
import { Container, Row, Col, Card, Button, ProgressBar } from "react-bootstrap";
import MusicPlayer from "./MusicPlayer";
import SessionNavView from "./SessionNavView";
import SessionOverlayController from "../controller/SessionOverlayController";



const SessionView = ({
    data, setIsMusicEnded, voteForSong, 
    endedSession, leaveSession, overlayIsOpen, 
    setOverlayIsOpen, getAllParticipants, listOfParticipants, 
    setSelectedMusicId, selectedMusicId,
    showcodeAndPassword, setShowcodeAndPassword, showListParticipants, setShowListParticipants}) => {

    console.log(data);
    console.log(data.owner);

    return (
        <Container fluid className="session-view" style={{position : "absolute"}}>
            {overlayIsOpen ? <SessionOverlayController
                overlayIsOpen={overlayIsOpen}
                setOverlayIsOpen={setOverlayIsOpen}
                sessionCode={data.joinSessionDto.qrCodeInfo}
                sessionPassword={data.joinSessionDto.password}
                listOfParticipants={listOfParticipants}
                showListParticipants={showListParticipants}
                showcodeAndPassword={showcodeAndPassword}
                setShowListParticipants={setShowListParticipants}
                setShowcodeAndPassword={setShowcodeAndPassword}
               /> 
            : null}
                           
            <Row className="justify-content-center align-items-start full-height ">
                <Col className="">
                    <SessionNavView 
                        userCount={data.numberParticipants} 
                        playedMusicCount={data.numberSongs} 
                        owner={data.owner}
                        userRole={"user"}
                        endedSession={endedSession}
                        leaveSession={leaveSession}
                        setOverlayIsOpen={setOverlayIsOpen}
                        getAllParticipants={getAllParticipants}
                        setShowListParticipants={setShowListParticipants}
                        setShowcodeAndPassword={setShowcodeAndPassword}
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
                                    setIsMusicEnded={setIsMusicEnded}
                                />
                        </div>
                        <Card.Body className="vote-container">

                            <div>
                                <h3 className="mb-3">Votez pour la prochaine musique :</h3>
                                {data.musics.musics.map((music, index) => (
                                    <div className="btn-container">
                                    <Button
                                        key={index}
                                        className={`song-button m-3 ${selectedMusicId === music.suggestedMusic ? 'selected-button' : ''}`}
                                        onClick={() => voteForSong(music.suggestedMusic)}
                                    >
                                        {music.name + " - " + music.artist}
                                    </Button>
                                    </div>
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
