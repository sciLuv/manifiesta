import React, { useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { MusicVoteView } from './view/MusicVoteView';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import SpotifyCallbackComponent from './controller/SpotifyCallbackComponent'; // Mettez à jour le chemin selon votre structure
import AccountCreationController from './controller/AccountCreationController';
import { URI_BASE } from '../env';
import { Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';
import LoginController from './controller/LoginController';

function App() {
  const [accessToken, setAccessToken] = useState("");
  const [refreshToken, setRefreshToken] = useState("");
  const [user, setUser] = useState("");
  const [mail, setMail] = useState("");


  useEffect(() => {
    console.log(user);
    console.log(mail);
    console.log(accessToken);
    console.log(refreshToken);
    }, []); 


/*   useEffect(() => {
    const socket = new SockJS(URI_BASE + '/public/websocket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connecté: ' + frame);
      stompClient.subscribe('/public/topic', (message) => {
        console.log("test");
        setCompteur(JSON.parse(message.body));
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []); */




  return (
    
    <div>

          <Router>
            <div>
              
              <Navbar expand="lg" className='border-bottom'>
                <Container>
                  <Navbar.Brand as={Link} className='text-light' to="#home">Manifiesta</Navbar.Brand>
                  <Navbar.Toggle aria-controls="basic-navbar-nav" className="nav-custom" />
                  <Navbar.Collapse  id="basic-navbar-nav">
                    <Nav className="me-auto">
                      <Nav.Link as={Link} className='text-light' to="/home">Accueil</Nav.Link>
                      <Nav.Link as={Link} className='text-light' to="/spotify">spotify</Nav.Link>
                      <Nav.Link as={Link} className='text-light' to="/account">créer un compte</Nav.Link>
                      <Nav.Link as={Link} className='text-light' to="/login">Se connecter</Nav.Link>
                      <NavDropdown title="Plus" id="basic-nav-dropdown">
                        <NavDropdown.Item as={Link} to="/action/3.1">Action</NavDropdown.Item>
                      </NavDropdown>
                    </Nav>
                  </Navbar.Collapse>
                </Container>
              </Navbar>

              <Routes>
                <Route path="/home" element={<div>Accueil</div>} />
                <Route path="/account" element={<AccountCreationController />} />
                <Route path="/login" element={<LoginController 
                      accessToken={accessToken}
                      refreshToken={refreshToken}
                      setAccessToken={setAccessToken}
                      setRefreshToken={setRefreshToken}
                      user={user}
                      setUser={setUser}
                      mail={mail}
                      setMail={setMail}
                />} />
              </Routes>
            </div>
          </Router>
    </div>   
  );
}

export default App;
