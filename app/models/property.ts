export interface PropertyResponse {
    properties: Property[];
}

export interface Property {
    id: number;
    address: string;
    description: string;
    epc: string;

    floorplan: string;
    garden: boolean;
    isBuy: boolean;
    isNew?: boolean;
    lot_area: string;
    name: string;
    nb_bath: number;
    nb_bed: number;
    nb_garage: number;
    price: string;
    year_build: number

    type: string

    images: string[];

}