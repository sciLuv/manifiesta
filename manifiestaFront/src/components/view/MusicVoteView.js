import React, { useEffect, useState } from 'react';


export function MusicVoteView(){
    const [progressWidth, setProgressWidth] = useState(40);

    const handleProgressClick = () => {
        setProgressWidth(prevWidth => Math.min(prevWidth + 1, 100));
        
    };

    return(
        <div className="progress-container">
            <div className="progress" onClick={handleProgressClick}>
                <div className="progress-bar" style={{ width: `${progressWidth}%` }}></div>
            </div>
        </div>
    )
}