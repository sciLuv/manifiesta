import React from 'react';
import { useNavigate } from 'react-router-dom';

const ErrorPage = () => {
  return (
    <div  className="text-light" style={{ textAlign: 'center', marginTop: '50px' }}>
        <h3>Page non trouvée</h3>
        <p>La page que vous recherchez n'existe pas. <br/>
        <a href="/">revenir sur la page d'acceuil</a>.</p>
    </div>
  );
};

export default ErrorPage;