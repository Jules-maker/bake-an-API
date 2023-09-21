import dayjs from 'dayjs';
import { IImage } from 'app/shared/model/image.model';

export interface IText {
  id?: string;
  coordinateX?: number | null;
  coordinateY?: number | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  imageId?: IImage | null;
}

export const defaultValue: Readonly<IText> = {};
