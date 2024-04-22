/* import React, { useEffect } from 'react';
import { URI_BASE } from '../../env';

const Incrementer = () => {
  // Utilisation de l'API fetch pour envoyer une requête GET à /incrementer
  fetch(URI_BASE + '/public/incrementer')
    .then(response => {
      if (!response.ok) {
        throw new Error('La requête a échoué');
      }
      return response.text();
    })
    .then(data => console.log(data))
    .catch(error => console.error('Erreur lors de l\'incrémentation:', error));
};

export default Incrementer; 
*/
