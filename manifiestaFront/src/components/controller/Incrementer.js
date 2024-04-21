/* import React, { useEffect } from 'react';
import { URI_BASE } from '../../env';

const Incrementer = () => {
  useEffect(() => {
    fetch(URI_BASE + 'public/incrementer')
      .then(response => response.text()) // ou .json() si le serveur renvoie du JSON
      .then(message => {
        console.log(message); // Log la réponse du serveur
      })
      .catch(error => {
        console.error('Erreur lors de la requête:', error);
      });
  }, []);

  return (
    <button onClick={handleIncrement}>Incrémenter</button>
  );
};

export default Incrementer; */
