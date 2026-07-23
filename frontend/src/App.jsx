import { useState, useEffect } from 'react';
import { getCampaigns, deleteCampaign } from './services/Api';
import CampaignList from './components/CampaignList.jsx';
import CampaignForm from './components/CampaignForm';

function App() {
  const [campaigns, setCampaigns] = useState([]);
  const [editingCampaign, setEditingCampaign] = useState(null);

  const [emeraldBalance, setEmeraldBalance] = useState(1000.00);

  useEffect(() => {
    fetchCampaigns();
  }, []);

  const fetchCampaigns = async () => {
    try {
      const data = await getCampaigns();
      setCampaigns(data);
    } catch (error) {
      console.error("Error fetching campaigns:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteCampaign(id);
      setCampaigns(campaigns.filter(c => c.id !== id));
    } catch (error) {
      console.error("Error deleting campaign:", error);
    }
  };

  const handleEdit = (campaign) => {
    setEditingCampaign(campaign);
  };

  const handleFormSuccess = (isNew, deductedFund) => {
    setEditingCampaign(null);
    fetchCampaigns();

    if (isNew && deductedFund) {
      setEmeraldBalance(prev => prev - deductedFund);
    }
  };

  return (
      <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '20px', fontFamily: 'sans-serif' }}>
        <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '2px solid #eee', paddingBottom: '10px', marginBottom: '20px' }}>
          <h1>Campaign Manager</h1>
          <div style={{ fontSize: '1.2rem', fontWeight: 'bold', color: '#2ecc71' }}>
            Emerald Balance: ${emeraldBalance.toFixed(2)}
          </div>
        </header>

        <div style={{ display: 'flex', gap: '40px' }}>
          <div style={{ flex: 1 }}>
            <CampaignForm
                campaignToEdit={editingCampaign}
                onSuccess={handleFormSuccess}
                onCancel={() => setEditingCampaign(null)}
            />
          </div>
          <div style={{ flex: 2 }}>
            <CampaignList
                campaigns={campaigns}
                onEdit={handleEdit}
                onDelete={handleDelete}
            />
          </div>
        </div>
      </div>
  );
}

export default App;