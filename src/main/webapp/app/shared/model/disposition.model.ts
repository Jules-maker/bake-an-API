import dayjs from 'dayjs';
import { IMeme } from 'app/shared/model/meme.model';

export interface IDisposition {
  id?: string;
  name?: string;
  sizeRequired?: number;
  coordinate1X?: number | null;
  coordinate1Y?: number | null;
  coordinate2X?: number | null;
  coordinate2Y?: number | null;
  coordinate3X?: number | null;
  coordinate3Y?: number | null;
  coordinate4X?: number | null;
  coordinate4Y?: number | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  memeId?: IMeme | null;
}

export const defaultValue: Readonly<IDisposition> = {};
