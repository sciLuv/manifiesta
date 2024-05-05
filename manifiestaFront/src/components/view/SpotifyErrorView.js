import React from 'react';
import { useNavigate } from 'react-router-dom';

const SpotifyErrorPage = () => {
  return (
    <div  className="text-light" style={{ textAlign: 'center', marginTop: '50px' }}>
        <h3>Page non trouvée</h3>
        <p>Il y a eu un problème de connexion à spotify, veuillez vous reconnecter. <br/>
        <a href="/login">revenir sur la page de connexion</a>.</p>
    </div>
  );
};

export default SpotifyErrorPage;