import React, { useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { URI_BASE } from '../../env';


const SpotifyCallback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  
  useEffect(() => {
    const code = searchParams.get('code');
    if (code) {
      const timer = setTimeout(() => {
        fetch(URI_BASE + '/spotify/callback', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
            'Refresh-Token': localStorage.getItem('refreshToken'),
          },
          body: JSON.stringify({ code }),
        })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
          console.log("est ce que spotifyToken est défini ? " + data.spotifyToken);
          if(data.spotifyToken.length > 0) {
            localStorage.setItem('spotifyToken', data.spotifyToken);
            localStorage.setItem('spotifyRefreshToken', data.spotifyRefreshToken);
          } else {
            localStorage.setItem('spotifyConnectionError', "Il y a eu une erreur lors de la connexion à Spotify. Veuillez réessayer.")
          }
          navigate('/create-new-session');  // Déplacer ici
        })
        .catch((error) => {
          console.error('Error:', error);
          localStorage.setItem('spotifyConnectionError', "Il y a eu une erreur lors de la connexion à Spotify. Veuillez réessayer.")
          navigate('/create-new-session'); 
        });
      }, 1000); // Attendre 1 seconde avant d'exécuter le fetch
      return () => clearTimeout(timer);
    }
  }, [searchParams, navigate]);

  return (
    <div className='text-light'>Authentification en cours...</div>
  );
};

export default SpotifyCallback;
