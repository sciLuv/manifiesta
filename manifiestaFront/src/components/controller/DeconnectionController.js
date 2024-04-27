import React,{ useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';

const DeconnectionController = ({ setAccessToken, setRefreshToken, setUser, setMail, setRole }) => {
    const navigate = useNavigate();

    const Deconnect = () => {
        console.log("Deconnection");
        setAccessToken("");
        setRefreshToken("");
        setUser("");
        setMail("");
        setRole("");
        navigate("/");  // Assurez-vous que le chemin "/home" est correct et défini dans vos Routes
    };

    useEffect(() => {
        Deconnect();
    }, []);

    return (
        <>
        <div>
            <h1>Deconnection</h1>
        </div>
        </>
    );
}

export default DeconnectionController;
