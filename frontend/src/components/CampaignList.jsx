import React from 'react';

const CampaignList = ({ campaigns, onEdit, onDelete }) => {
    if (!campaigns || campaigns.length === 0) {
        return (
            <div style={{ padding: '20px', backgroundColor: 'var(--code-bg)', border: '1px solid var(--border)', borderRadius: '8px' }}>
                No campaigns found. Create one!
            </div>
        );
    }

    return (
        <div>
            <h2>Your Campaigns</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                <thead>
                <tr style={{ backgroundColor: 'var(--code-bg)', borderBottom: '2px solid var(--border)' }}>
                    <th style={{ padding: '10px' }}>Name</th>
                    <th style={{ padding: '10px' }}>Keywords</th>
                    <th style={{ padding: '10px' }}>Bid ($)</th>
                    <th style={{ padding: '10px' }}>Fund ($)</th>
                    <th style={{ padding: '10px' }}>Town</th>
                    <th style={{ padding: '10px' }}>Status</th>
                    <th style={{ padding: '10px' }}>Actions</th>
                </tr>
                </thead>
                <tbody>
                {campaigns.map((campaign) => (
                    <tr key={campaign.id} style={{ borderBottom: '1px solid var(--border)' }}>
                        <td style={{ padding: '10px' }}>{campaign.name}</td>
                        <td style={{ padding: '10px' }}>{campaign.keywords}</td>
                        <td style={{ padding: '10px' }}>{campaign.bidAmount.toFixed(2)}</td>
                        <td style={{ padding: '10px' }}>{campaign.campaignFund.toFixed(2)}</td>
                        <td style={{ padding: '10px' }}>{campaign.town}</td>
                        <td style={{ padding: '10px' }}>
                <span style={{
                    padding: '4px 8px',
                    borderRadius: '12px',
                    backgroundColor: campaign.status ? 'rgba(39, 174, 96, 0.15)' : 'rgba(192, 57, 43, 0.15)',
                    color: campaign.status ? '#2ecc71' : '#e74c3c',
                    fontSize: '0.85rem'
                }}>
                  {campaign.status ? 'ON' : 'OFF'}
                </span>
                        </td>
                        <td style={{ padding: '10px' }}>
                            <button onClick={() => onEdit(campaign)} style={{ marginRight: '5px', cursor: 'pointer', color: 'var(--text-h)', backgroundColor: 'transparent', border: '1px solid var(--border)', padding: '4px 8px', borderRadius: '4px' }}>Edit</button>
                            <button onClick={() => onDelete(campaign.id)} style={{ color: '#e74c3c', cursor: 'pointer', backgroundColor: 'transparent', border: '1px solid #e74c3c', padding: '4px 8px', borderRadius: '4px' }}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CampaignList;