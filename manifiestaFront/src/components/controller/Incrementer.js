import React, { useEffect } from 'react';

const Incrementer = () => {
  useEffect(() => {
    fetch('http://127.0.0.1:8080/incrementer')
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

export default Incrementer;
