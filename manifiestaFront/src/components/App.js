import React, { useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { URI_BASE } from '../env';
import { BrowserRouter as Router, Routes, Route, Link} from 'react-router-dom';
import SpotifyCallback from './controller/SpotifyCallback'; // Mettez à jour le chemin selon votre structure
import { Navbar, Nav, Image } from 'react-bootstrap';

import logo from '../img/logo.png';

import AccountCreationController from './controller/AccountCreationController';
import SessionParameterController from './controller/SessionParameterController';
import  SessionController  from './controller/SessionController';
import DeconnectionController from './controller/DeconnectionController';
import JoinSessionController from './controller/JoinSessionController';
import LoginController from './controller/LoginController';
import HomeController from './controller/HomeController';

function App() {
  const [accessToken, setAccessToken] = useState("");
  const [refreshToken, setRefreshToken] = useState("");
  const [user, setUser] = useState("");
  const [mail, setMail] = useState("");
  const [role, setRole] = useState("");

  console.log();

  useEffect(() => {
    console.log(user);
    console.log(mail);
    console.log(accessToken);
    console.log(refreshToken);
    console.log(role);
    }, [accessToken]);

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
                  <Navbar.Brand as={Link} to="/" className="text-light d-flex align-items-center">
                    <Image 
                      src={logo}
                      width="60"   // Vous pouvez utiliser des unités relatives comme 'rem' si vous préférez
                      height="60"  // Vous pouvez utiliser des unités relatives comme 'rem' si vous préférez
                      className="d-inline-block align-top mr-2"
                      alt="Logo Manifiesta"
                    />
                    {/* Utilisation des classes de Bootstrap pour gérer la taille de la police de manière responsive */}
                    <h1 className="mb-0 h4 h5-sm">Manifiesta</h1>
                  </Navbar.Brand>
                  <Navbar.Toggle aria-controls="basic-navbar-nav" className="nav-custom" />
                  <Navbar.Collapse  id="basic-navbar-nav">
                    <Nav className="ml-auto">
                      {role === "" && (
                        <>
                          <Nav.Link as={Link} className='text-light' to="/account">Créer un compte</Nav.Link>
                          <Nav.Link as={Link} className='text-light' to="/login">Se connecter</Nav.Link>
                        </>
                      )}
                      {role === "user" && (
                        <>
                          <Nav.Link as={Link} className='text-light' to="/join-session" >Rejoindre une session</Nav.Link>
                          <Nav.Link as={Link} className='text-light' to="/deconnexion">Se déconnecter</Nav.Link>
                        </>
                      )}
                    </Nav>
                  </Navbar.Collapse>
              </Navbar>

              <Routes>
                <Route path="/" element={<HomeController 
                          role={role} 
                          user={user}
                          accessToken={accessToken}
                          refreshToken={refreshToken}
                />} />
                <Route path="/create-new-session" element={<SessionParameterController
                          accessToken={accessToken}
                          setAccessToken={setAccessToken}
                          refreshToken={refreshToken}
                          setRefreshToken={setRefreshToken}
                          user={user}
                          setUser={setUser}
                          mail={mail}
                          setMail={setMail}
                          role={role}
                          setRole={setRole}
                />} />
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
                      role={role}
                      setRole={setRole}
                />} />
                <Route path="/spotify" element={<SpotifyCallback />} />
                <Route path="/session" element={<SessionController />} />
                <Route path="/deconnexion" element={
                    <DeconnectionController
                        setAccessToken={setAccessToken}
                        setRefreshToken={setRefreshToken}
                        setUser={setUser}
                        setMail={setMail}
                        setRole={setRole}
                    />
                } />
                <Route path="/join-session" element={<JoinSessionController 
                      accessToken={accessToken}
                      refreshToken={refreshToken}
                />} />
              </Routes>
            </div>
          </Router>
    </div>   
  );
}

export default App;
