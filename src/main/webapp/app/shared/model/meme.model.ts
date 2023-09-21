import dayjs from 'dayjs';
import { IImage } from 'app/shared/model/image.model';
import { IDisposition } from 'app/shared/model/disposition.model';
import { Tags } from 'app/shared/model/enumerations/tags.model';

export interface IMeme {
  id?: string;
  slot?: number;
  title?: string | null;
  tag?: Tags | null;
  isDraft?: boolean | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  images?: IImage[] | null;
  dispositions?: IDisposition[] | null;
}

export const defaultValue: Readonly<IMeme> = {
  isDraft: false,
};
