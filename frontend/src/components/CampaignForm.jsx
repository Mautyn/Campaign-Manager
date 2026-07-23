import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import { createCampaign, updateCampaign } from '../services/Api';

const TOWNS = ["Warsaw", "Krakow", "Gdansk", "Wroclaw", "Poznan"];

const KEYWORD_OPTIONS = [
    { value: 'shoes', label: 'Shoes' },
    { value: 'summer', label: 'Summer' },
    { value: 'sale', label: 'Sale' },
    { value: 'electronics', label: 'Electronics' },
    { value: 'toys', label: 'Toys' }
];

const CampaignForm = ({ campaignToEdit, onSuccess, onCancel }) => {
    const [formData, setFormData] = useState({
        name: '',
        keywords: [],
        bidAmount: '',
        campaignFund: '',
        status: true,
        town: TOWNS[0],
        radius: ''
    });

    const [errors, setErrors] = useState({});

    useEffect(() => {
        if (campaignToEdit) {
            setFormData({
                ...campaignToEdit,
                keywords: campaignToEdit.keywords.split(', ').map(k => ({ value: k, label: k }))
            });
            setErrors({});
        } else {
            resetForm();
        }
    }, [campaignToEdit]);

    const resetForm = () => {
        setFormData({
            name: '',
            keywords: [],
            bidAmount: '',
            campaignFund: '',
            status: true,
            town: TOWNS[0],
            radius: ''
        });
        setErrors({});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});

        const payload = {
            ...formData,
            bidAmount: parseFloat(formData.bidAmount),
            campaignFund: parseFloat(formData.campaignFund),
            radius: parseInt(formData.radius, 10),
            keywords: formData.keywords ? formData.keywords.map(k => k.value).join(', ') : ''
        };

        try {
            if (campaignToEdit) {
                await updateCampaign(campaignToEdit.id, payload);
                onSuccess(false, null);
            } else {
                await createCampaign(payload);
                onSuccess(true, payload.campaignFund);
            }
            resetForm();
        } catch (err) {
            if (err.response && err.response.data) {
                setErrors(err.response.data);
            } else {
                console.error("Unknown error:", err);
            }
        }
    };

    const inputStyle = {
        width: '100%',
        padding: '8px',
        marginBottom: '10px',
        boxSizing: 'border-box',
        backgroundColor: 'var(--bg)',
        color: 'var(--text-h)',
        border: '1px solid var(--border)',
        borderRadius: '4px'
    };

    return (
        <div style={{ backgroundColor: 'var(--code-bg)', border: '1px solid var(--border)', padding: '20px', borderRadius: '8px' }}>
            <h2>{campaignToEdit ? 'Edit Campaign' : 'Create Campaign'}</h2>

            {errors.error && <div style={{ color: 'red', marginBottom: '10px', fontWeight: 'bold' }}>{errors.error}</div>}

            <form onSubmit={handleSubmit}>
                <label>Campaign Name *</label>
                <input
                    type="text"
                    style={inputStyle}
                    value={formData.name}
                    onChange={(e) => setFormData({...formData, name: e.target.value})}
                />
                {errors.name && <span style={{color: 'red', fontSize: '0.8rem'}}>{errors.name}</span>}

                <label style={{display: 'block', marginTop: '10px'}}>Keywords *</label>
                <Select
                    isMulti
                    options={KEYWORD_OPTIONS}
                    value={formData.keywords}
                    onChange={(selected) => setFormData({...formData, keywords: selected})}
                    placeholder="Type or select keywords..."
                />
                {errors.keywords && <div style={{color: 'red', fontSize: '0.8rem', marginTop: '5px'}}>{errors.keywords}</div>}

                <label style={{display: 'block', marginTop: '15px'}}>Bid Amount (min 1.00) *</label>
                <input
                    type="number" step="0.01" style={inputStyle}
                    value={formData.bidAmount}
                    onChange={(e) => setFormData({...formData, bidAmount: e.target.value})}
                />
                {errors.bidAmount && <span style={{color: 'red', fontSize: '0.8rem'}}>{errors.bidAmount}</span>}

                <label>Campaign Fund (min 10.00) *</label>
                <input
                    type="number" step="0.01" style={inputStyle}
                    value={formData.campaignFund}
                    onChange={(e) => setFormData({...formData, campaignFund: e.target.value})}
                    disabled={!!campaignToEdit}
                />
                {errors.campaignFund && <span style={{color: 'red', fontSize: '0.8rem'}}>{errors.campaignFund}</span>}

                <label>Town</label>
                <select
                    style={inputStyle}
                    value={formData.town}
                    onChange={(e) => setFormData({...formData, town: e.target.value})}
                >
                    {TOWNS.map(town => <option key={town} value={town}>{town}</option>)}
                </select>

                <label>Radius (km) *</label>
                <input
                    type="number" style={inputStyle}
                    value={formData.radius}
                    onChange={(e) => setFormData({...formData, radius: e.target.value})}
                />
                {errors.radius && <span style={{color: 'red', fontSize: '0.8rem'}}>{errors.radius}</span>}

                <label style={{ display: 'flex', alignItems: 'center', marginTop: '10px', marginBottom: '20px' }}>
                    <input
                        type="checkbox"
                        checked={formData.status}
                        onChange={(e) => setFormData({...formData, status: e.target.checked})}
                        style={{ marginRight: '10px' }}
                    />
                    Campaign is Active (ON)
                </label>

                <div style={{ display: 'flex', gap: '10px' }}>
                    <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#3498db', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                        {campaignToEdit ? 'Update' : 'Create'}
                    </button>
                    {campaignToEdit && (
                        <button type="button" onClick={onCancel} style={{ padding: '10px 20px', backgroundColor: '#95a5a6', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                            Cancel
                        </button>
                    )}
                </div>
            </form>
        </div>
    );
};

export default CampaignForm;