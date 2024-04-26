import React from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";


const SessionView = (props) => {
    let stringJson = '{"musics":[{"name": "DVNO", "artist": "Justice", "album": "† (Anniversary Edition)", "duration": "236133", "url_img": "https://i.scdn.co/image/ab67616d0000b2737dd568cee992457bca91cc8d", "url_link": "spotify:track:6GBXkJqq2kcNWEZFzFfqdm"},{"name": "Cercle rouge", "artist": "Disiz", "album": "Disizilla", "duration": "236600", "url_img": "https://i.scdn.co/image/ab67616d0000b27320f839e57854a45d495b29b8", "url_link": "spotify:track:7CEu61ZExU4ngZDc2cr5E8"},{"name": "Blackbird - Remastered 2009", "artist": "The Beatles", "album": "The Beatles (Remastered)", "duration": "138386", "url_img": "https://i.scdn.co/image/ab67616d0000b2734ce8b4e42588bf18182a1ad2", "url_link": "spotify:track:5jgFfDIR6FR0gvlA56Nakr"},{"name": "Walking Underwater", "artist": "The M Machine", "album": "Glare", "duration": "293671", "url_img": "https://i.scdn.co/image/ab67616d0000b2734c8668195642ca3ac51ecdf0", "url_link": "spotify:track:5vXwfnyJl2zOf1bcHQrk0t"},{"name": "Saga tome 1", "artist": "So La Lune", "album": "Orbite", "duration": "207971", "url_img": "https://i.scdn.co/image/ab67616d0000b273c8d4468fc000b49a52a71091", "url_link": "spotify:track:6Li0UQUXen3K2spqvUJSxk"}], "currentlyPlayingMusic" : {"name": "DVNO", "artist": "Justice", "album": "Justice", "duration": "236133", "url_img": "https://i.scdn.co/image/ab67616d0000b2731c0bcf8b536295438d26c70d", "url_link": "spotify:track:7pdDBqpilpdW7c40DpFMW9"},"currentMusicInformation": {"timeStamp": "1714123802123","progressMs": "42398"},"sessionInformations" :  {"sessionName": "","sessionCode": "GRSZABwn6I1R","codeIsGlobal": "false"}}'
    // Données simulées, remplacer par les props si les données sont passées du parent
    const data = JSON.parse(stringJson);

    return (
        <Container fluid className="session-view ">
            <Row className="justify-content-center align-items-start full-height ">
                <Col className="d-flex justify-content-start">
                    <Card className="text-center dark-card bg-dark dflex-session">
                        <Card.Img variant="top" src={data.currentlyPlayingMusic.url_img} className="album-image" />
                        <Card.Body>
                            <Card.Title className="text-orange">{data.currentlyPlayingMusic.name}</Card.Title>
                            <Card.Text className="border-bottom pb-3">
                                <span className="text-orange">Artiste : </span>
                                {data.currentlyPlayingMusic.artist}
                                <span className="text-orange">  Album : </span>
                                {data.currentlyPlayingMusic.album}
                                </Card.Text>
                            <div className="">
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
