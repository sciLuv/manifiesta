import React, { useState } from 'react';
import ReactDom from 'react-dom';
import { Button, Dropdown } from 'react-bootstrap';
import DotsIcon from '../../img/ellipsis-vertical.svg'; 
import UserImg from '../../img/user.svg';
import MusicImg from '../../img/music.svg';

const SessionNavView = ({ 
    userCount, playedMusicCount, owner, endedSession, 
    leaveSession, setOverlayIsOpen, getAllParticipants, 
    setShowListParticipants, setShowcodeAndPassword}) => {

    const toggleMenu = () => setShowMenu(!showMenu);



    return (
        <div className="header-options p-3 bg-dark">
            <div>
                <span>
                <img src={UserImg} alt="Menu" style={{ width: '20px', height: '20px' }} />  {userCount} 
                <img src={MusicImg} alt="Menu" style={{ width: '20px', height: '20px', marginLeft : '30px' }} /> {playedMusicCount}</span>
            </div>
            <div>
                <Dropdown>
                    <Dropdown.Toggle variant="success" id="dropdown-basic">
                    <img src={DotsIcon} alt="Menu" style={{ width: '20px', height: '20px' }} /> 
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                        <Dropdown.Item href="#/action-1" onClick={
                                    () => {
                                        setOverlayIsOpen(true);
                                        setShowListParticipants(true);
                                        getAllParticipants();
                                    }
                                }
                        >Voir la liste des participants</Dropdown.Item>
                        {owner ? 
                                <>
                                    <Dropdown.Item href="#/stop-session" onClick={ () => endedSession()}>Arrêter la session</Dropdown.Item> 
                                    <Dropdown.Item href="#/see-participants" onClick={
                                            () =>{
                                                setOverlayIsOpen(true)
                                                setShowcodeAndPassword(true)
                                            }
                                        }
                                    >Voir le code de session</Dropdown.Item>
                                </>
                            : 
                            <Dropdown.Item href="#/leave-session" onClick={ () => leaveSession()}>Quitter la session</Dropdown.Item>
                        }
                        
                        
                        
                    </Dropdown.Menu>
                </Dropdown>
            </div>
        </div>
    );
};

export default SessionNavView;