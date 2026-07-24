import logging
import os

import numpy as np
from openai import AsyncOpenAI

logger = logging.getLogger("ai-service.embeddings")

_client = AsyncOpenAI(api_key=os.getenv("OPENAI_API_KEY"))
EMBEDDING_MODEL = "text-embedding-3-small"


async def get_embedding(text: str) -> list[float]:
    truncated = text[:8000]
    response = await _client.embeddings.create(
        model=EMBEDDING_MODEL,
        input=truncated,
    )
    return response.data[0].embedding


def cosine_similarity(a: list[float], b: list[float]) -> float:
    vec_a = np.array(a)
    vec_b = np.array(b)
    denom = np.linalg.norm(vec_a) * np.linalg.norm(vec_b)
    if denom == 0:
        return 0.0
    return float(np.dot(vec_a, vec_b) / denom)