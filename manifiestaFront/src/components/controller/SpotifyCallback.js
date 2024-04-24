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
          localStorage.setItem('spotifyToken', data.spotifyToken);
          localStorage.setItem('spotifyRefreshToken', data.spotifyRefreshToken);
          navigate('/create-new-session');  // Déplacer ici
        })
        .catch((error) => {
          console.error('Error:', error);
        });
      }, 1000); // Attendre 1 seconde avant d'exécuter le fetch
      return () => clearTimeout(timer);
    } else {
      navigate('/'); // Redirigez l'utilisateur vers la page d'accueil en cas d'erreur
    }
  }, [searchParams, navigate]);

  return (
    <div className='text-light'>Authentification en cours...</div>
  );
};

export default SpotifyCallback;



/* 

  //c'était dans app avant
  const [spotifyAuthorizationUri, setSpotifyAuthorizationUri] = useState('');

  const handleAuthorization = async () => {
    try {
      const response = await fetch(URI_BASE + '/spotify/authorize');
      if (response.ok) {
        const authorizationUri = await response.text();
        setSpotifyAuthorizationUri(authorizationUri);
        // Rediriger l'utilisateur vers l'URI de redirection Spotify
        window.location.href = authorizationUri;
      } else {
        console.error('Erreur lors de la récupération de l\'URI de redirection Spotify');
      }
    } catch (error) {
      console.error('Erreur lors de la récupération de l\'URI de redirection Spotify', error);
    }
  };
*/