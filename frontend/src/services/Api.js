import axios from 'axios';

const API_URL = 'https://campaign-manager-vbvf.onrender.com/api/campaigns';

const ACCOUNT_ID = 1;

export const getCampaigns = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const createCampaign = async (campaignData) => {
    const response = await axios.post(`${API_URL}?accountId=${ACCOUNT_ID}`, campaignData);
    return response.data;
};

export const updateCampaign = async (id, campaignData) => {
    const response = await axios.put(`${API_URL}/${id}`, campaignData);
    return response.data;
};

export const deleteCampaign = async (id) => {
    await axios.delete(`${API_URL}/${id}`);
};