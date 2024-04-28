import React, { useState, useEffect } from 'react';
import { ProgressBar } from 'react-bootstrap';

const MusicPlayer = (props) => {
    const [currentSongTime, setCurrentSongTime] = useState(parseInt(props.currentSongProgress));
    const duration = parseInt(props.duration); // Assurez-vous que c'est en millisecondes
    const [progress, setProgress] = useState(0);

    useEffect(() => {
        setCurrentSongTime(parseInt(props.currentSongProgress)); // Réinitialise currentSongTime lorsque la chanson change
        setProgress(0); // Réinitialise la progression lorsque la chanson change
        props.setIsMusicEnded(false);
    }, [props.currentSongProgress]);

    useEffect(() => {
        const intervalId = setInterval(() => {
            if (currentSongTime < duration) {
                setCurrentSongTime(prevTime => prevTime + 500); // Augmente de 1000 ms chaque seconde
            } else {
                props.setIsMusicEnded(true);
                clearInterval(intervalId); // Arrête l'intervalle si la durée est dépassée
            }
        }, 500);

        return () => clearInterval(intervalId); // Nettoyage de l'intervalle
    }, [currentSongTime, duration]);

    useEffect(() => {
        const progressPercentage = (currentSongTime / duration);
        setProgress(Math.trunc(progressPercentage*100));
    }, [currentSongTime, duration]); // Mise à jour de la progression chaque fois que currentSongTime ou duration change

    return (
        <div>
            <ProgressBar now={progress} label={`${progress}%`} />
        </div>
    );
}

export default MusicPlayer;