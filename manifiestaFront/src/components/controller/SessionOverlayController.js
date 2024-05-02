import React from "react";
import SessionOverlayView from "../view/SessionOverlayView";

const SessionOverlayController = ({
    setOverlayIsOpen, sessionPassword, sessionCode, 
    listOfParticipants, showListParticipants, showcodeAndPassword,
    setShowListParticipants, setShowcodeAndPassword}) => {

    return (
        <SessionOverlayView 
            setOverlayIsOpen={setOverlayIsOpen}
            sessionPassword={sessionPassword}
            sessionCode={sessionCode}
            listOfParticipants={listOfParticipants}
            showListParticipants={showListParticipants}
            showcodeAndPassword={showcodeAndPassword}
            setShowListParticipants={setShowListParticipants}
            setShowcodeAndPassword={setShowcodeAndPassword}
        />
    )
}

export default SessionOverlayController;

        