import dayjs from 'dayjs';
import { IText } from 'app/shared/model/text.model';
import { IMeme } from 'app/shared/model/meme.model';

export interface IImage {
  id?: string;
  position?: number | null;
  url?: string;
  createdAt?: string | null;
  updatedAt?: string | null;
  texts?: IText[] | null;
  memeId?: IMeme | null;
}

export const defaultValue: Readonly<IImage> = {};
