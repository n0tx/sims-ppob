# Base image
FROM bitnami/minio:2022

# Expose MinIO ports
EXPOSE 9000 9001

# Set environment variables
ENV MINIO_ROOT_USER=admin \
    MINIO_ROOT_PASSWORD=password \
    MINIO_DEFAULT_BUCKETS=users-profiles \
    MINIO_IMAGESDIRECTORY=images

# Ensure the container runs with Railway's dynamic port configuration
CMD ["minio", "server", "/data", "--console-address", ":9001"]
