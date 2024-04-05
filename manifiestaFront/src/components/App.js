import React, { useState, useContext } from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

import { myContext } from '../index';

export default function App() {
    return (
       <myContext.Provider>
        <h1>Manifiesta</h1>
            <BrowserRouter>
            </BrowserRouter>
       </myContext.Provider>
    );
}

