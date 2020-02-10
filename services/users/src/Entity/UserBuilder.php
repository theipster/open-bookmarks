<?php

declare(strict_types=1);

namespace App\Entity;

use Ramsey\Uuid\Uuid;
use Ramsey\Uuid\UuidInterface;

class UserBuilder
{
    private UuidInterface $uuid;
    private string $username;

    public function build(): User
    {
        return new User($this->uuid, $this->username);
    }

    public function generateUuid(): UserBuilder
    {
        $uuid = Uuid::uuid4();
        return $this->uuid($uuid);
    }

    public function uuid(UuidInterface $uuid): UserBuilder
    {
        $builder = clone $this;
        $builder->uuid = $uuid;
        return $builder;
    }

    public function username(string $username): UserBuilder
    {
        $builder = clone $this;
        $builder->username = $username;
        return $builder;
    }
}
