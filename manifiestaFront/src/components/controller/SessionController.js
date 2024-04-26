
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SessionView from '../view/SessionView';

const SessionController = () => {

    
    let stringJson = localStorage.getItem('sessionInformations');
    const data = JSON.parse(stringJson);

    // Gerer la selection du nombre de chansons
    const handleSongsNumberChange = (event) => {
        setSongsNumber(event.target.value);
    }


    return (
        <>
            <SessionView
                stringJson={data}
            />
        </>
    );
};

export default SessionController;
