/* import React, { useContext } from 'react';
import { accessToken, refreshToken } from '../index';

export default function UseHeaderStorage(response) {
    const [, setAccessTokenStored] = useContext(accessToken);
    const [, setRefreshTokenStored] = useContext(refreshToken);

    // Récupérer les tokens depuis les headers de la réponse
    const newAccessToken = response.headers.get('Access-Token');
    const newRefreshToken = response.headers.get('Refresh-Token');

    // Vérifier que les deux tokens sont présents avant de mettre à jour les états
    if (newAccessToken && newRefreshToken) {
        setAccessTokenStored(newAccessToken); // Corriger ici pour utiliser newAccessToken
        setRefreshTokenStored(newRefreshToken);
    }

    // Toujours retourner la réponse
    return response;

} */
