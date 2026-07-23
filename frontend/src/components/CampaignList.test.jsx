import { render, screen } from '@testing-library/react';
import { describe, it, expect } from 'vitest';
import CampaignList from './CampaignList';

describe('CampaignList Component', () => {

    it('should render empty state message when no campaigns are provided', () => {
        // Render the component with an empty array
        render(<CampaignList campaigns={[]} />);

        // Check if the appropriate text appears on the screen
        expect(screen.getByText('No campaigns found. Create one!')).toBeInTheDocument();
    });

    it('should render a list of campaigns', () => {
        // Prepare mock data based on your model
        const mockCampaigns = [
            { id: 1, name: 'New Campaign', bidAmount: 5.00, campaignFund: 100.00 }
        ];

        render(<CampaignList campaigns={mockCampaigns} />);

        // Check if the campaign name was rendered correctly
        expect(screen.getByText('New Campaign')).toBeInTheDocument();
    });
});