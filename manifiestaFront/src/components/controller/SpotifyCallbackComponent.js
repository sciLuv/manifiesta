import React, { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { URI_BASE } from '../../env';

const SpotifyCallbackComponent = () => {
  let [searchParams] = useSearchParams();
  let code = searchParams.get("code");

  useEffect(() => {
    if (code) {
      // Ajout d'un délai pour laisser le temps à la page de se charger après la redirection
      const timer = setTimeout(() => {
        fetch(URI_BASE + 'spotify/callback', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ code }),
        })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      }, 1000); // Attendre 1 seconde avant d'exécuter le fetch
  
      return () => clearTimeout(timer);
    }
  }, [code]);
  
  // Affichez quelque chose de pertinent ici, par exemple un message de chargement
  return <div>Chargement...</div>;
};

export default SpotifyCallbackComponent;